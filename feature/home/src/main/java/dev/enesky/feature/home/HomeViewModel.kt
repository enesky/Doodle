package dev.enesky.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.common.result.Result
import dev.enesky.core.common.result.asResult
import dev.enesky.core.data.models.AnimeFilter
import dev.enesky.core.data.response.AnimeResponse
import dev.enesky.core.domain.mappers.asAnime
import dev.enesky.core.domain.mappers.pagingMap
import dev.enesky.core.domain.usecase.AnimeUseCase
import dev.enesky.core.domain.usecase.TopAnimePagingUseCase
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
                .pagingMap(AnimeResponse::asAnime)
                .cachedIn(viewModelScope)

            val upcomingAnimesFlow = topAnimePagingUseCase(AnimeFilter.UPCOMING)
                .pagingMap(AnimeResponse::asAnime)
                .cachedIn(viewModelScope)

            val popularAnimesFlow = topAnimePagingUseCase(AnimeFilter.POPULARITY)
                .pagingMap(AnimeResponse::asAnime)
                .cachedIn(viewModelScope)

            val favoriteAnimesFlow = topAnimePagingUseCase(AnimeFilter.FAVORITE)
                .pagingMap(AnimeResponse::asAnime)
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
                .asResult()
                .onEach { resource ->
                    when (resource) {
                        is Result.Loading -> {
                            updateUiState {
                                copy(
                                    loading = true,
                                    previewAnime = null,
                                )
                            }
                        }

                        is Result.Success -> {
                            if (resource.data.id == 0) {
                                updateUiState {
                                    copy(
                                        loading = false,
                                        previewAnime = null,
                                    )
                                }
                                return@onEach
                            }
                            val anime = resource.data.asAnime()
                            updateUiState {
                                copy(
                                    loading = false,
                                    previewAnime = anime,
                                )
                            }
                        }

                        is Result.Error -> {
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
