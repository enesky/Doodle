package dev.enesky.feature.login.manager

import android.content.Intent
import android.content.IntentSender
import androidx.activity.ComponentActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dev.enesky.core.common.utils.Logger
import dev.enesky.feature.login.BuildConfig
import kotlinx.coroutines.tasks.await

/**
 * Created by Enes Kamil YILMAZ on 19/11/2023
 */

/**
 * Authentication Manager
 * Handles all authentication related operations
 *
 * @param activity activity
 * @param oneTapClient one tap client
 */
class AuthManager(
    private val activity: ComponentActivity,
    private val oneTapClient: SignInClient,
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
            oneTapClient.signOut().await()
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
            .addOnCompleteListener(activity) { task ->
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
            .addOnCompleteListener(activity) { task ->
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
            .addOnCompleteListener(activity) { task ->
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
                .addOnCompleteListener(activity) { task ->
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
     * Google Sign In with IntentSender
     */
    suspend fun signInGoogle(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest(),
            ).await()
        } catch (e: Exception) {
            Logger.error("AuthManager", "signInGoogle: ${e.message}", e)
            null
        }
        return result?.pendingIntent?.intentSender
    }

    /**
     * Google Sign In with Intent
     */
    suspend fun signInGoogleWithIntent(intent: Intent) {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
        } catch (e: Exception) {
            Logger.error("AuthManager", "signInGoogleWithIntent: ${e.message}", e)
        }
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
