package dev.enesky.feature.login.signin

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.consts.ErrorMessages
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.data.LoginType
import dev.enesky.feature.login.manager.AuthManager
import dev.enesky.feature.login.signin.helpers.SignInEvents
import dev.enesky.feature.login.signin.helpers.SignInUiState
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class SignInViewModel(
    private val authManager: AuthManager,
) : ViewModel(),
    UiState<SignInUiState> by UiStateDelegate(initialState = { SignInUiState() }),
    Event<SignInEvents> by EventDelegate() {

    // ------------------ EMAIL ------------------

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val signInResult = authManager.signInWithEmailAndPassword(email, password)
            setState {
                copy(
                    loginType = LoginType.EMAIL,
                    loginResult = signInResult,
                )
            }
            handleResults(signInResult.data != null)
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
                    loginType = LoginType.GOOGLE,
                    loginResult = signInResult,
                )
            }
            handleResults(isSignInSuccessful = signInResult.data != null)
        }
    }

    // ------------------ ANONYMOUS ------------------

    fun signInAnonymously() {
        viewModelScope.launch {
            val signInResult = authManager.signInAnonymously()
            setState {
                copy(
                    loginType = LoginType.ANONYMOUS,
                    loginResult = signInResult,
                )
            }
            handleResults(isSignInSuccessful = signInResult.data != null)
        }
    }

    // ------------------ EVENTS ------------------

    private fun handleResults(
        isSignInSuccessful: Boolean,
    ) {
        viewModelScope.launch {
            event.trigger(
                if (isSignInSuccessful) {
                    SignInEvents.NavigateToHome
                } else {
                    SignInEvents.OnError(currentState.loginResult?.errorMessage ?: ErrorMessages.GENERAL_ERROR)
                },
            )
        }
    }
}
