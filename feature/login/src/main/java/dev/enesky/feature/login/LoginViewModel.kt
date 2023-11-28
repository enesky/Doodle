package dev.enesky.feature.login

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.feature.login.manager.AuthManager
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class LoginViewModel(
    private val authManager: AuthManager,
) : ViewModel(),
    UiState<LoginUiState> by UiStateDelegate(initialState = { LoginUiState() }),
    Event<LoginEvents> by EventDelegate() {

    // ------------------ EMAIL ------------------

    fun signInWithEmailAndPassword(email: String, password: String) {
        // Helper functions
        fun signInEmailResult(
            signInResult: SignInResult,
            isSignInSuccessful: Boolean,
        ) {
            setState {
                copy(
                    authType = AuthType.EMAIL,
                    signInResult = signInResult,
                )
            }
            navigateToHome(isSignInSuccessful)
        }

        suspend fun signUpWithEmail(email: String, password: String) {
            val resultFromSignUp: SignInResult = authManager.signUpWithEmailAndPassword(
                email = email,
                password = password,
            )
            signInEmailResult(resultFromSignUp, resultFromSignUp.data != null)
        }

        // Main function
        viewModelScope.launch {
            val signInResult = authManager.signInWithEmailAndPassword(email, password)
            when {
                signInResult.data != null -> {
                    signInEmailResult(signInResult, true)
                }
                signInResult.errorMessage?.contains("no user record") == true -> {
                    signUpWithEmail(email, password)
                }
                else -> {
                    signInEmailResult(signInResult, false)
                }
            }
        }
    }

    // ------------------ GOOGLE ------------------

    fun signInWithGoogle(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
    ) {
        viewModelScope.launch {
            val signInIntentSender = authManager.signInWithGoogle()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch,
                ).build(),
            )
        }
    }

    fun signInWithGoogleResult(intent: Intent) {
        viewModelScope.launch {
            val signInResult = authManager.signInWithGoogleResult(intent)
            setState {
                copy(
                    authType = AuthType.GOOGLE,
                    signInResult = signInResult,
                )
            }
            navigateToHome(isSignInSuccessful = signInResult.data != null)
        }
    }

    // ------------------ ANONYMOUS ------------------

    fun signInAnonymously() {
        viewModelScope.launch {
            val signInResult = authManager.signInAnonymously()
            setState {
                copy(
                    authType = AuthType.ANONYMOUS,
                    signInResult = signInResult,
                )
            }
            navigateToHome(isSignInSuccessful = signInResult.data != null)
        }
    }

    // ------------------ EVENTS ------------------

    private fun navigateToHome(
        isSignInSuccessful: Boolean,
    ) {
        viewModelScope.launch {
            event.trigger(
                newEvent =
                if (isSignInSuccessful) {
                    LoginEvents.NavigateToHome
                } else {
                    LoginEvents.OnError(currentState.signInResult?.errorMessage ?: "")
                },
            )
        }
    }
}
