package dev.enesky.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.consts.ErrorMessages
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.IEvent
import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.manager.AuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 05/01/2024
 */

const val SPLASH_DELAY = 1500L

class SplashViewModel(
    private val authManager: AuthManager,
) : ViewModel(),
    UiState<SplashUiState> by UiStateDelegate(initialState = { SplashUiState() }),
    Event<SplashEvents> by EventDelegate() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            checkUser()
            delay(SPLASH_DELAY)
            triggerNavigation()
        }
    }

    private suspend fun checkUser() {
        updateUiState {
            copy(isUserLoggedIn = authManager.isUserLoggedIn())
        }
    }

    private suspend fun triggerNavigation() {
        triggerEvent {
            when (uiState.value.isUserLoggedIn) {
                true -> SplashEvents.OnNavigateToHomeScreen
                false -> SplashEvents.OnNavigateToLoginScreen
                else -> SplashEvents.OnError(ErrorMessages.GENERAL_ERROR)
            }
        }
    }
}

data class SplashUiState(
    val isUserLoggedIn: Boolean? = null,
) : IUiState

sealed interface SplashEvents : IEvent {
    data class OnError(val errorMessage: String) : SplashEvents
    data object OnNavigateToLoginScreen : SplashEvents
    data object OnNavigateToHomeScreen : SplashEvents
}
