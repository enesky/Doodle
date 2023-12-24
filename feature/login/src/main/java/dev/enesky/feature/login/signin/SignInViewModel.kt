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
import dev.enesky.core.common.enums.LoginType
import dev.enesky.core.domain.manager.AuthManager
import dev.enesky.core.domain.models.LoginResult
import dev.enesky.feature.login.signin.helpers.SignInEvents
import dev.enesky.feature.login.signin.helpers.SignInUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class SignInViewModel(
    private val authManager: AuthManager,
) : ViewModel(),
    UiState<SignInUiState> by UiStateDelegate(initialState = { SignInUiState() }),
    Event<SignInEvents> by EventDelegate() {

    init {
        val initialDelay = 500L
        viewModelScope.launch {
            delay(initialDelay)
            signInWithCredentialApi()
        }
    }

    // ------------- CREDENTIAL API  -------------

    private suspend fun signInWithCredentialApi() {
        authManager.getCredentials(
            onEmailSignIn = { email, password ->
                signInWithEmailAndPassword(email, password)
            },
            onGoogleSignIn = { idToken ->
                signInWithGoogleResult(
                    idToken = idToken,
                )
            },
        )
    }

    // ------------------ EMAIL ------------------

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val signInResult = authManager.signInWithEmailAndPassword(email, password)
            updateUiState {
                copy(
                    loginType = LoginType.EMAIL,
                    loginResult = signInResult,
                )
            }
            handleResults(loginResult = signInResult)
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            val forgotPasswordResult = authManager.forgotPassword(email)
            updateUiState {
                copy(
                    loginType = LoginType.EMAIL,
                    loginResult = forgotPasswordResult,
                )
            }
            handleResults(
                loginResult = forgotPasswordResult,
                shouldNavigateToHome = false,
            )
        }
    }

    // ------------------ GOOGLE ------------------

    fun signInWithGoogleLauncher(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
    ) {
        viewModelScope.launch {
            val signInIntentSender = authManager.signInWithGoogleLauncher()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch,
                ).build(),
            )
        }
    }

    fun signInWithGoogleResult(
        intent: Intent? = null,
        idToken: String? = null,
    ) {
        viewModelScope.launch {
            val signInResult = authManager.signInWithGoogleResult(intent, idToken)
            updateUiState {
                copy(
                    loginType = LoginType.GOOGLE,
                    loginResult = signInResult,
                )
            }
            handleResults(loginResult = signInResult)
        }
    }

    // ------------------ ANONYMOUS ------------------

    fun signInAnonymously() {
        viewModelScope.launch {
            val signInResult = authManager.signInAnonymously()
            updateUiState {
                copy(
                    loginType = LoginType.ANONYMOUS,
                    loginResult = signInResult,
                )
            }
            handleResults(loginResult = signInResult)
        }
    }

    // ------------------ EVENTS ------------------

    private fun handleResults(
        loginResult: LoginResult,
        shouldNavigateToHome: Boolean = true,
    ) {
        viewModelScope.launch {
            triggerEvent {
                if (loginResult.isSignInSuccessful == true && shouldNavigateToHome) {
                    SignInEvents.NavigateToHome
                } else {
                    SignInEvents.OnError(
                        currentState.loginResult?.errorMessage ?: ErrorMessages.GENERAL_ERROR,
                    )
                }
            }
        }
    }

    fun navigateToSignUp() {
        viewModelScope.launch {
            triggerEvent {
                SignInEvents.NavigateToSignUp
            }
        }
    }
}
