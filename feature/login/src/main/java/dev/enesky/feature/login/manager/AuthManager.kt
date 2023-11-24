package dev.enesky.feature.login.manager

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dev.enesky.core.common.utils.Logger
import dev.enesky.feature.login.BuildConfig
import dev.enesky.feature.login.SignInResult
import dev.enesky.feature.login.UserData
import kotlinx.coroutines.tasks.await
import java.util.concurrent.Executor
import kotlin.coroutines.cancellation.CancellationException

/**
 * Created by Enes Kamil YILMAZ on 19/11/2023
 */

/**
 * Authentication Manager
 * Handles all authentication related operations
 *
 * @param executor Executor
 * @param signInClient SignInClient
 */
class AuthManager(
    private val executor: Executor,
    private val signInClient: SignInClient,
) {

    // ------------------ COMMON ------------------

    private val auth by lazy { Firebase.auth }

    /**
     * Checks if user is logged in or not
     *
     * @return true if user is logged in, false otherwise
     */
    suspend fun isUserLoggedIn(): Boolean = auth.currentUser != null

    /**
     * Gets current user
     *
     * @return current user
     */
    suspend fun getFirebaseUser(): FirebaseUser? = auth.currentUser

    /**
     * Signs out user
     */
    suspend fun signOut() {
        try {
            signInClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            Logger.error("AuthManager", "signOut: ${e.message}", e)
        }
    }

    // ------------------ EMAIL AUTH ------------------

    /**
     * Creates user with email and password
     *
     * @param email email of the user
     * @param password password of the user
     */
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(executor) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onSuccess()
                } else {
                    onError()
                }
            }
    }

    /**
     * Signs in user with email and password
     *
     * @param email email of the user
     * @param password password of the user
     */
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(executor) { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            }
    }

    // ------------------ ANONYMOUS SIGN IN ------------------

    /**
     * Checks if user is anonymous or not
     */
    suspend fun isUserAnonymous(): Boolean = getFirebaseUser()?.isAnonymous == true

    /**
     * Signs in user anonymously
     */
    suspend fun signInAnonymously(
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        auth.signInAnonymously()
            .addOnCompleteListener(executor) { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            }
    }

    /**
     * Links anonymous user to Email Auth user or Google user
     * Run this after [createUserWithEmailAndPassword] or [loginWithGoogleCredentials]
     *
     * @param email email of the user if user is created with email and password
     * @param password password of the user if user is created with email and password
     * @param googleIdToken google id token of the user if user is created with google credentials
     */
    suspend fun linkWithCredentials(
        email: String? = null,
        password: String? = null,
        googleIdToken: String? = null,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        getFirebaseUser()?.let {
            it.linkWithCredential(linkAnonymousUser(email, password, googleIdToken))
                .addOnCompleteListener(executor) { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onError()
                    }
                }
        }
    }

    /**
     * Links anonymous user to Email Auth user or Google user
     */
    private suspend fun linkAnonymousUser(
        email: String? = null,
        password: String? = null,
        googleIdToken: String? = null,
    ): AuthCredential = when {
        isUserAnonymous().not() -> {
            throw IllegalStateException("User is not anonymous")
        }

        email != null && password != null && googleIdToken == null -> {
            EmailAuthProvider.getCredential(email, password)
        }

        email == null && password == null && googleIdToken != null -> {
            GoogleAuthProvider.getCredential(googleIdToken, null)
        }

        else -> {
            throw IllegalStateException("Email and password or Google Id Token must be provided")
        }
    }

    // ------------------ GOOGLE SIGN IN ------------------

    /**
     * Google Sign In with Intent
     */
    suspend fun signInGoogleInitial(intent: Intent): SignInResult {
        val credential = signInClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        email = email,
                        profilePictureUrl = photoUrl?.toString(),
                    )
                }
            )
        } catch (e: Exception) {
            Logger.error("AuthManager", "signInGoogleWithIntent: ${e.message}", e)
            if (e is CancellationException) throw e
            SignInResult(
                errorMessage = e.message,
            )
        }
    }

    /**
     * Google Sign In with IntentSender
     */
    suspend fun signInGoogleFinal(): IntentSender? {
        return try {
            signInClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            Logger.error("AuthManager", "signInGoogle: ${e.message}", e)
            when (e) {
                is CancellationException -> throw e
                is ApiException -> {
                    if (e.statusCode.toString().startsWith("16")) {
                        //16: Cannot find a matching credential.
                        //Happens when user doesn't have any saved credentials -> didn't signed in with google before
                        Logger.error("AuthManager", "User didn't signed in to any Google Account before")
                    }
                    null
                }
                else -> null
            }
        }?.pendingIntent?.intentSender
    }

    /**
     * Builds sign in request
     */
    private fun buildSignInRequest(): BeginSignInRequest {
        val getSignInRequestOptions = BeginSignInRequest
            .GoogleIdTokenRequestOptions
            .builder()
            .setSupported(true)
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.DOODLE_GOOGLE_API_KEY)
            .build()

        return BeginSignInRequest
            .Builder()
            .setGoogleIdTokenRequestOptions(getSignInRequestOptions)
            .setAutoSelectEnabled(true)
            .build()
    }
}
