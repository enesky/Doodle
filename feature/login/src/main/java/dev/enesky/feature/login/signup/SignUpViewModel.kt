package dev.enesky.feature.login.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.consts.ErrorMessages
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.data.LoginResult
import dev.enesky.feature.login.manager.AuthManager
import dev.enesky.feature.login.signup.helpers.SignUpEvents
import dev.enesky.feature.login.signup.helpers.SignUpUiState
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

class SignUpViewModel(
    private val authManager: AuthManager,
) : ViewModel(),
    UiState<SignUpUiState> by UiStateDelegate(initialState = { SignUpUiState() }),
    Event<SignUpEvents> by EventDelegate() {

    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            val resultFromSignUp: LoginResult = authManager.signUpWithEmailAndPassword(
                email = email,
                password = password,
            )
            updateUiState {
                copy(
                    loginResult = resultFromSignUp,
                )
            }
            if (resultFromSignUp.data != null) {
                authManager.saveCredentialForEmailAuth(email, password)
            }
            handleResults(resultFromSignUp.data != null)
        }
    }

    // ------------------ EVENTS ------------------

    private fun handleResults(
        isSignInSuccessful: Boolean,
    ) {
        viewModelScope.launch {
            triggerEvent {
                if (isSignInSuccessful) {
                    SignUpEvents.NavigateToHome
                } else {
                    SignUpEvents.OnError(
                        currentState.loginResult?.errorMessage ?: ErrorMessages.GENERAL_ERROR,
                    )
                }
            }
        }
    }
}
