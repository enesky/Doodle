package dev.enesky.feature.login.manager

import android.content.Context
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dev.enesky.core.common.consts.ErrorMessages
import dev.enesky.core.common.utils.Logger
import dev.enesky.feature.login.BuildConfig

/**
 * Created by Enes Kamil YILMAZ on 01/12/2023
 */

class CredentialApiManager(
    private val context: Context,
) {

    private val credentialManager by lazy { CredentialManager.create(context) }

    /**
     * Sign up with credential manager
     * Saves the credential for the given email and password.
     */
    suspend fun saveCredentialForEmailAuth(email: String, password: String) {
        try {
            credentialManager.createCredential(
                request = CreatePasswordRequest(email, password),
                context = context,
            )
        } catch (e: CreateCredentialCancellationException) {
            Logger.debug(javaClass.simpleName, "User cancelled the save flow", e)
        } catch (e: CreateCredentialException) {
            Logger.debug(javaClass.simpleName, "Failed to save credential", e)
        } catch (e: Exception) {
            Logger.debug(javaClass.simpleName, ErrorMessages.GENERAL_ERROR, e)
        }
    }

    /**
     * Sign in with credential manager
     * Retrieves the user's saved password for your app from their password provider.
     *
     * @param onEmailSignIn: Callback for email sign in
     * @param onGoogleSignIn: Callback for Google sign in
     */
    suspend fun getCredentials(
        onEmailSignIn: (email: String, password: String) -> Unit,
        onGoogleSignIn: (idToken: String) -> Unit,
    ) {
        val googleIdCredentialOption = GetGoogleIdOption.Builder()
            .setServerClientId(BuildConfig.DOODLE_GOOGLE_API_KEY)
            .build()
        try {
            val result = credentialManager.getCredential(
                context = context,
                request = GetCredentialRequest(
                    listOf(
                        GetPasswordOption(),
                        googleIdCredentialOption,
                    ),
                ),
            )
            handleSignIn(result, onEmailSignIn, onGoogleSignIn)
        } catch (e: GetCredentialException) {
            Logger.debug(javaClass.simpleName, ErrorMessages.GENERAL_ERROR, e)
        } catch (e: NoCredentialException) {
            Logger.debug(javaClass.simpleName, "No credential available", e)
        } catch (e: Exception) {
            Logger.debug(javaClass.simpleName, ErrorMessages.GENERAL_ERROR, e)
        }
    }

    /**
     * Handles the sign in result
     *
     * @param result: Credential result
     * @param onEmailSignIn: Callback for email sign in
     * @param onGoogleSignIn: Callback for Google sign in
     */
    private fun handleSignIn(
        result: GetCredentialResponse,
        onEmailSignIn: (email: String, password: String) -> Unit,
        onGoogleSignIn: (idToken: String) -> Unit,
    ) {
        when (val credential = result.credential) {
            is PasswordCredential -> { // Email and password credential
                val username = credential.id
                val password = credential.password
                onEmailSignIn(username, password)
            }

            is CustomCredential -> { // Google Sign in credential
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        onGoogleSignIn(idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Logger.debug(
                            javaClass.simpleName,
                            "Received an invalid google id token response",
                            e,
                        )
                    }
                } else {
                    Logger.debug(javaClass.simpleName, "Unexpected type of credential")
                }
            }

            else -> {
                Logger.debug(javaClass.simpleName, "Unexpected type of credential")
            }
        }
    }
}
