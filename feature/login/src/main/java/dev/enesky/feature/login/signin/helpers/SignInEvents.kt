package dev.enesky.feature.login.signin.helpers

import dev.enesky.core.common.delegate.IEvent

/**
 * Created by Enes Kamil YILMAZ on 28/11/2023
 */

sealed interface SignInEvents : IEvent {
    data class OnError(val errorMessage: String) : SignInEvents
    data object NavigateToHome : SignInEvents
}
