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
import dev.enesky.core.common.consts.Values
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.common.utils.Logger
import dev.enesky.core.data.response.LoginResponse
import dev.enesky.core.data.response.User
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import java.util.concurrent.Executor
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume

/**
 * Created by Enes Kamil YILMAZ on 19/11/2023
 */

/**
 * Authentication Manager
 * Handles all authentication related operations
 *
 * @param executor Executor for Google Sign In
 * @param signInClient SignInClient for Google Sign In
 * @param credentialApiManager CredentialApiManager
 */
class AuthManager(
    private val executor: Executor,
    private val signInClient: SignInClient,
    private val credentialApiManager: CredentialApiManager,
) {

    // ------------------ COMMON ------------------

    private val auth by lazy { Firebase.auth }
    private val googleClientId by lazy { get().get<String>(named(Values.GOOGLE_WEB_CLIENT_ID)) }

    /**
     * Checks if user is logged in or not
     *
     * @return true if user is logged in, false otherwise
     */
    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    /**
     * Gets current user
     *
     * @return current user
     */
    fun getFirebaseUser(): FirebaseUser? = auth.currentUser

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
     * Signs in user with email and password
     *
     * @param email email of the user
     * @param password password of the user
     */
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): LoginResponse {
        return suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor) { task ->
                    if (task.isSuccessful) {
                        continuation.resume(
                            value = LoginResponse(
                                user = User(
                                    userId = task.result.user?.uid ?: String.Empty,
                                    email = email,
                                ),
                            ),
                        )
                    } else {
                        continuation.resume(
                            LoginResponse(errorMessage = task.exception?.message),
                        )
                    }
                }
        }
    }

    /**
     * Creates user with email and password
     *
     * @param email email of the user
     * @param password password of the user
     */
    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    ): LoginResponse {
        return suspendCancellableCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor) { task ->
                    if (task.isSuccessful) {
                        continuation.resume(
                            LoginResponse(
                                user = User(
                                    userId = task.result.user?.uid ?: String.Empty,
                                    email = email,
                                ),
                            ),
                        )
                    } else {
                        continuation.resume(
                            LoginResponse(errorMessage = task.exception?.message),
                        )
                    }
                }
        }
    }

    suspend fun forgotPassword(email: String): LoginResponse {
        return suspendCancellableCoroutine { continuation ->
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(executor) { task ->
                    if (task.isSuccessful) {
                        continuation.resume(
                            LoginResponse(
                                user = User(
                                    userId = String.Empty,
                                    email = email,
                                ),
                            ),
                        )
                    } else {
                        continuation.resume(
                            LoginResponse(errorMessage = task.exception?.message),
                        )
                    }
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
    suspend fun signInAnonymously(): LoginResponse {
        return suspendCancellableCoroutine { continuation ->
            auth.signInAnonymously()
                .addOnCompleteListener(executor) { task ->
                    if (task.isSuccessful) {
                        continuation.resume(
                            LoginResponse(
                                user = User(
                                    userId = task.result.user?.uid ?: String.Empty,
                                ),
                            ),
                        )
                    } else {
                        continuation.resume(
                            LoginResponse(errorMessage = task.exception?.message),
                        )
                    }
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
    suspend fun signInWithGoogleResult(
        intent: Intent? = null,
        idToken: String? = null,
    ): LoginResponse {
        val googleIdToken = when {
            intent != null -> signInClient.getSignInCredentialFromIntent(intent).googleIdToken
            idToken != null -> idToken
            else -> throw IllegalStateException("Google Id Token must be provided")
        }
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            LoginResponse(
                user = user?.run {
                    User(
                        userId = uid,
                        username = displayName,
                        email = email,
                        profilePictureUrl = photoUrl?.toString(),
                    )
                },
            )
        } catch (e: Exception) {
            Logger.error("AuthManager", "signInGoogleWithIntent: ${e.message}", e)
            if (e is CancellationException) throw e
            LoginResponse(
                errorMessage = e.message,
            )
        }
    }

    /**
     * Google Sign In with IntentSender
     */
    suspend fun signInWithGoogleLauncher(): IntentSender? {
        return try {
            signInClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            Logger.error("AuthManager", "signInGoogle: ${e.message}", e)
            when (e) {
                is CancellationException -> throw e
                is ApiException -> {
                    if (e.statusCode.toString().startsWith("16")) {
                        // 16: Cannot find a matching credential.
                        // Happens when user doesn't have any saved credentials -> didn't signed in with google before
                        Logger.error(
                            "AuthManager",
                            "User didn't signed in to any Google Account before",
                        )
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
            .setServerClientId(googleClientId)
            .build()

        return BeginSignInRequest
            .Builder()
            .setGoogleIdTokenRequestOptions(getSignInRequestOptions)
            .setAutoSelectEnabled(true)
            .build()
    }

    // ------------------ CREDENTIALS ------------------

    suspend fun getCredentials(
        onEmailSignIn: (email: String, password: String) -> Unit,
        onGoogleSignIn: (idToken: String) -> Unit,
    ) {
        credentialApiManager.getCredentials(
            onEmailSignIn = onEmailSignIn,
            onGoogleSignIn = onGoogleSignIn,
        )
    }

    suspend fun saveCredentialForEmailAuth(
        email: String,
        password: String,
    ) {
        credentialApiManager.saveCredentialForEmailAuth(
            email = email,
            password = password,
        )
    }
}
