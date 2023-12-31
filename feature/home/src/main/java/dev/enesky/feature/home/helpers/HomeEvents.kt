package dev.enesky.feature.home.helpers

import dev.enesky.core.common.delegate.IEvent

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

sealed interface HomeEvents : IEvent {
    data class OnError(val errorMessage: String) : HomeEvents
    data class OnItemOptionsClick(val animeId: String) : HomeEvents
    data class NavigateToDetails(val animeId: String) : HomeEvents
}
