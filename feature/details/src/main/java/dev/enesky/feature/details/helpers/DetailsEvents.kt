package dev.enesky.feature.details.helpers

import dev.enesky.core.common.delegate.IErrorEvent
import dev.enesky.core.common.delegate.IEvent

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

sealed interface DetailsEvents : IEvent {
    data class OnError(override val errorMessage: String) : DetailsEvents, IErrorEvent
    data class OnTrailerPlayClick(val animeId: String) : DetailsEvents
}
