package dev.enesky.feature.login

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.feature.login.manager.AuthManager
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class LoginViewModel(
    private val authManager: AuthManager,
) : ViewModel(), UiState<LoginUiState> by UiStateDelegate({ LoginUiState() }) {

    fun clickSignInWithGoogle(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
    ) {
        viewModelScope.launch {
            val signInIntentSender = authManager.signInGoogleFinal()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch,
                ).build(),
            )
        }
    }
    fun signInGoogleWithIntent(intent: Intent) {
        viewModelScope.launch {
            val signInResult = authManager.signInGoogleInitial(intent)
            setState {
                copy(
                    authType = AuthType.GOOGLE,
                    signInResult = signInResult,
                    isSignInSuccessful = signInResult.data != null,
                )
            }
        }
    }
}
