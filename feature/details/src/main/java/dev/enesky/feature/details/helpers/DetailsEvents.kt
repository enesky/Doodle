package dev.enesky.feature.home.helpers

import dev.enesky.core.common.delegate.IEvent

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

sealed interface DetailsEvents : IEvent {
    data class OnError(val errorMessage: String) : DetailsEvents
    data class onTrailerPlayClick(val animeId: String) : DetailsEvents
}
