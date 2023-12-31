package dev.enesky.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.common.enums.AnimeFilter
import dev.enesky.core.common.result.Result
import dev.enesky.core.common.result.asResult
import dev.enesky.core.domain.usecase.DetailedAnimeUseCase
import dev.enesky.core.domain.usecase.TopAnimePagingUseCase
import dev.enesky.feature.home.helpers.HomeEvents
import dev.enesky.feature.home.helpers.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class HomeViewModel(
    private val topAnimePagingUseCase: TopAnimePagingUseCase,
    private val detailedAnimeUseCase: DetailedAnimeUseCase,
) : ViewModel(),
    UiState<HomeUiState> by UiStateDelegate(initialState = { HomeUiState() }),
    Event<HomeEvents> by EventDelegate() {

    init {
        getAllAnimes()
        getPreviewAnime()
    }

    private fun getAllAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            val popularAnimesFlow = topAnimePagingUseCase(AnimeFilter.POPULARITY)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)

            val airingAnimesFlow = topAnimePagingUseCase(AnimeFilter.AIRING)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)

            val upcomingAnimesFlow = topAnimePagingUseCase(AnimeFilter.UPCOMING)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)

            val favoriteAnimesFlow = topAnimePagingUseCase(AnimeFilter.FAVORITE)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)

            updateUiState {
                copy(
                    loading = false,
                    popularAnimes = popularAnimesFlow,
                    airingAnimes = airingAnimesFlow,
                    upcomingAnimes = upcomingAnimesFlow,
                    favoriteAnimes = favoriteAnimesFlow,
                )
            }
        }
    }

    private fun getPreviewAnime() {
        val jjkAnimeId = 51009
        viewModelScope.launch(Dispatchers.IO) {
            detailedAnimeUseCase(animeId = jjkAnimeId)
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
                            updateUiState {
                                copy(
                                    loading = false,
                                    previewAnime = resource.data,
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
