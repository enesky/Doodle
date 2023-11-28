package dev.enesky.feature.login.helpers

import dev.enesky.core.common.delegate.IEvent

/**
 * Created by Enes Kamil YILMAZ on 28/11/2023
 */

sealed interface LoginEvents : IEvent {
    data class OnError(val errorMessage: String) : LoginEvents
    data object NavigateToHome : LoginEvents
}
