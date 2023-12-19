package dev.enesky.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.usecase.TopAnimePagingUseCase
import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.model.AnimeFilter
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
    private val topAnimePagingUseCase: TopAnimePagingUseCase,
) : ViewModel(),
    UiState<HomeUiState> by UiStateDelegate(initialState = { HomeUiState() }),
    Event<HomeEvents> by EventDelegate() {

    init {
        getAllAnimes()
    }

    private fun getAllAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            val airingAnimesFlow = topAnimePagingUseCase(AnimeFilter.AIRING)
                .pagingMap(Anime::asMiniAnime)
                .cachedIn(viewModelScope)

            val upcomingAnimesFlow = topAnimePagingUseCase(AnimeFilter.UPCOMING)
                .pagingMap(Anime::asMiniAnime)
                .cachedIn(viewModelScope)

            val popularAnimesFlow = topAnimePagingUseCase(AnimeFilter.POPULARITY)
                .pagingMap(Anime::asMiniAnime)
                .cachedIn(viewModelScope)

            val favoriteAnimesFlow = topAnimePagingUseCase(AnimeFilter.FAVORITE)
                .pagingMap(Anime::asMiniAnime)
                .cachedIn(viewModelScope)

            updateUiState {
                copy(
                    loading = false,
                    airingAnimes = airingAnimesFlow,
                    upcomingAnimes = upcomingAnimesFlow,
                    popularAnimes = popularAnimesFlow,
                    favoriteAnimes = favoriteAnimesFlow,
                )
            }
        }
    }
}
