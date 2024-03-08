package dev.enesky.feature.login.signup.helpers

import dev.enesky.core.common.delegate.IErrorEvent
import dev.enesky.core.common.delegate.IEvent

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

sealed interface SignUpEvents : IEvent {
    data class OnError(override val errorMessage: String) : SignUpEvents, IErrorEvent
    data object NavigateToHome : SignUpEvents
    data object NavigateToSignIn : SignUpEvents
}
