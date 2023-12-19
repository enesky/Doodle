package dev.enesky.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.usecase.AiringAnimePagingUseCase
import dev.enesky.core.domain.usecase.FavoriteAnimePagingUseCase
import dev.enesky.core.domain.usecase.PopularAnimePagingUseCase
import dev.enesky.core.domain.usecase.UpcomingAnimePagingUseCase
import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.model.asMiniAnime
import dev.enesky.core.ui.mapper.pagingMap
import dev.enesky.feature.home.helpers.HomeEvents
import dev.enesky.feature.home.helpers.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class HomeViewModel(
    private val airingAnimePagingUseCase: AiringAnimePagingUseCase,
    private val upcomingAnimePagingUseCase: UpcomingAnimePagingUseCase,
    private val favoriteAnimePagingUseCase: FavoriteAnimePagingUseCase,
    private val popularAnimePagingUseCase: PopularAnimePagingUseCase,
) : ViewModel(),
    UiState<HomeUiState> by UiStateDelegate(initialState = { HomeUiState() }),
    Event<HomeEvents> by EventDelegate() {

    init {
        getAllAnimes()
    }

    private fun getAllAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            val airingAnimesFlow = airingAnimePagingUseCase()
                .pagingMap(Anime::asMiniAnime)
                .cachedIn(viewModelScope)

            val upcomingAnimesFlow = upcomingAnimePagingUseCase()
                .pagingMap(Anime::asMiniAnime)
                .cachedIn(viewModelScope)

            val popularAnimesFlow = popularAnimePagingUseCase()
                .pagingMap(Anime::asMiniAnime)
                .cachedIn(viewModelScope)

            val favoriteAnimesFlow = favoriteAnimePagingUseCase()
                .pagingMap(Anime::asMiniAnime)
                .cachedIn(viewModelScope)

            updateUiState {
                copy(
                    loading = false,
                    airingAnimes = airingAnimesFlow,
                    upcomingAnimes = upcomingAnimesFlow,
                    popularAnimes = popularAnimesFlow,
                    favoriteAnimes = favoriteAnimesFlow
                )
            }
        }
    }

}
