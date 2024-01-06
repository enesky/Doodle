package dev.enesky.feature.splash

import androidx.lifecycle.ViewModel
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.IEvent
import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.manager.AuthManager

/**
 * Created by Enes Kamil YILMAZ on 05/01/2024
 */

class SplashViewModel(
    private val authManager: AuthManager,
) : ViewModel(),
    UiState<SplashUiState> by UiStateDelegate(initialState = { SplashUiState() }),
    Event<SplashEvents> by EventDelegate() {

    suspend fun checkUser() {
        triggerEvent {
            if (authManager.isUserLoggedIn()) {
                SplashEvents.OnNavigateToHomeScreen
            } else {
                SplashEvents.OnNavigateToLoginScreen
            }
        }
    }

}

data class SplashUiState(
    override val loading: Boolean = false,
    override var errorMessage: String? = null,
) : IUiState

sealed interface SplashEvents : IEvent {
    data class OnError(val errorMessage: String) : SplashEvents
    data object OnNavigateToLoginScreen : SplashEvents
    data object OnNavigateToHomeScreen : SplashEvents
}
