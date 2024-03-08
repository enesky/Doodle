package dev.enesky.core.common.delegate

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by Enes Kamil YILMAZ on 27/11/2023
 */

/**
 * Base interface for defining new Event data classes
 */
interface IEvent

interface IErrorEvent : IEvent {
    val errorMessage: String
}

/**
 * Base interface for event delegation
 */
interface Event<T : IEvent> {

    val event: Channel<T>
    val eventFlow: Flow<T>

    /**
     * Trigger event function for sending new events
     */
    suspend fun triggerEvent(newEvent: () -> T)
}

/**
 * Delegation class for event usage
 *
 * Usage:
 *
 *    class LoginViewModel : ViewModel(), Event<LoginEvents> by EventDelegate() {
 *      viewModelScope.launch {
 *          triggerEvent { LoginEvents.AnonymousSignInClick() }
 *      }
 *    }
 *
 *    ObserveAsEvents(viewModel.eventFlow) { event ->
 *      when (event) {
 *          is LoginEvents.AnonymousSignInClick -> { }
 *      }
 *    }
 *
 * @check ObserveAsEvents in core/common/src/main/java/dev/enesky/core/common/utils/ObserveAsEvents.kt
 */
class EventDelegate<T : IEvent> : Event<T> {

    override val event: Channel<T> = Channel()
    override val eventFlow: Flow<T> = event.receiveAsFlow()

    override suspend fun triggerEvent(newEvent: () -> T) {
        event.send(newEvent.invoke())
    }
}
