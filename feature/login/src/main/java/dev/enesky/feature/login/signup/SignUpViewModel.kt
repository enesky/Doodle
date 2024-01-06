package dev.enesky.feature.login.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.consts.ErrorMessages
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.manager.AuthManager
import dev.enesky.core.domain.models.LoginResult
import dev.enesky.feature.login.signup.helpers.SignUpEvents
import dev.enesky.feature.login.signup.helpers.SignUpUiState
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.IO) {
            val resultFromSignUp = authManager.signUpWithEmailAndPassword(
                email = email,
                password = password,
            )
            updateUiState {
                copy(
                    loginResult = resultFromSignUp,
                )
            }
            if (resultFromSignUp.isSignInSuccessful == true) {
                authManager.saveCredentialForEmailAuth(email, password)
            }
            handleResults(resultFromSignUp)
        }
    }

    // ------------------ EVENTS ------------------

    private fun handleResults(
        loginResult: LoginResult,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            triggerEvent {
                if (loginResult.isSignInSuccessful == true) {
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
