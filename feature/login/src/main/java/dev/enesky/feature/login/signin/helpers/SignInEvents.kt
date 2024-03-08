package dev.enesky.feature.login.signin.helpers

import dev.enesky.core.common.delegate.IErrorEvent
import dev.enesky.core.common.delegate.IEvent

/**
 * Created by Enes Kamil YILMAZ on 28/11/2023
 */

sealed interface SignInEvents : IEvent {
    data class OnError(override val errorMessage: String) : SignInEvents, IErrorEvent
    data object NavigateToHome : SignInEvents
    data object NavigateToSignUp : SignInEvents

}
