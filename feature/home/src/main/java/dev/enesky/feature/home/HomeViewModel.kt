package dev.enesky.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.usecase.AnimeUseCase
import dev.enesky.core.domain.usecase.TopAnimePagingUseCase
import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.model.AnimeFilter
import dev.enesky.core.network.model.asMiniAnime
import dev.enesky.core.network.util.Resource
import dev.enesky.core.network.util.asResource
import dev.enesky.core.ui.mapper.pagingMap
import dev.enesky.feature.home.helpers.HomeEvents
import dev.enesky.feature.home.helpers.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class HomeViewModel(
    private val topAnimePagingUseCase: TopAnimePagingUseCase,
    private val animeUseCase: AnimeUseCase,
) : ViewModel(),
    UiState<HomeUiState> by UiStateDelegate(initialState = { HomeUiState() }),
    Event<HomeEvents> by EventDelegate() {

    init {
        getAllAnimes()
        getPreviewAnime()
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

    private fun getPreviewAnime() {
        val jjkAnimeId = 40748
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase(animeId = jjkAnimeId)
                .asResource()
                .onEach { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            updateUiState {
                                copy(
                                    loading = true,
                                    previewAnime = null,
                                )
                            }
                        }

                        is Resource.Success -> {
                            if (resource.data.id == 0) {
                                updateUiState {
                                    copy(
                                        loading = false,
                                        previewAnime = null,
                                    )
                                }
                                return@onEach
                            }
                            val miniAnime = resource.data.asMiniAnime()
                            updateUiState {
                                copy(
                                    loading = false,
                                    previewAnime = miniAnime,
                                )
                            }
                        }

                        is Resource.Error -> {
                            updateUiState {
                                copy(
                                    loading = false,
                                    previewAnime = null,
                                )
                            }
                        }
                    }
                }.launchIn(this)
        }
    }
}
