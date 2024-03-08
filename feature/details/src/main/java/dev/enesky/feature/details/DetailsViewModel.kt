package dev.enesky.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.enesky.core.common.consts.getErrorMessage
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.common.result.Result
import dev.enesky.core.common.result.asResult
import dev.enesky.core.domain.usecase.AnimeCharactersUseCase
import dev.enesky.core.domain.usecase.AnimeEpisodesUseCase
import dev.enesky.core.domain.usecase.AnimeRecommendationsUseCase
import dev.enesky.core.domain.usecase.DetailedAnimeUseCase
import dev.enesky.feature.details.helpers.DetailsEvents
import dev.enesky.feature.details.helpers.DetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class DetailsViewModel(
    val detailedAnimeUseCase: DetailedAnimeUseCase,
    val animeCharactersUseCase: AnimeCharactersUseCase,
    val animeRecommendationsUseCase: AnimeRecommendationsUseCase,
    val animeEpisodesUseCase: AnimeEpisodesUseCase,
) : ViewModel(),
    UiState<DetailsUiState> by UiStateDelegate(initialState = { DetailsUiState() }),
    Event<DetailsEvents> by EventDelegate() {

    fun getThemAll(animeId: Int) {
        getDetailedAnime(animeId)
        getAnimeCharacters(animeId)
        getAnimeEpisodes(animeId)
        getAnimeRecommendations(animeId)
    }

    private fun getDetailedAnime(animeId: Int) {
        detailedAnimeUseCase(animeId = animeId)
            .asResult()
            .onEach { resource ->
                when (resource) {
                    is Result.Loading -> updateUiState { copy(isLoading = true) }
                    is Result.Success -> updateUiState {
                        copy(
                            isLoading = false,
                            detailedAnime = resource.data,
                        )
                    }
                    is Result.Error -> {
                        updateUiState {
                            copy(
                                isLoading = false,
                                detailedAnime = null,
                            )
                        }
                        triggerEvent {
                            DetailsEvents.OnError(
                                getErrorMessage(resource.exception),
                            )
                        }
                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun getAnimeCharacters(animeId: Int) {
        animeCharactersUseCase(animeId = animeId)
            .asResult()
            .onEach { resource ->
                when (resource) {
                    is Result.Loading -> updateUiState { copy(isLoading = true) }
                    is Result.Success -> updateUiState {
                        copy(
                            isLoading = false,
                            characters = resource.data,
                        )
                    }
                    is Result.Error -> {
                        updateUiState {
                            copy(
                                isLoading = false,
                                characters = null,
                            )
                        }
                        triggerEvent {
                            DetailsEvents.OnError(
                                getErrorMessage(resource.exception),
                            )
                        }
                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun getAnimeEpisodes(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val popularAnimesFlow = animeEpisodesUseCase(animeId)
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)

            updateUiState {
                copy(
                    isLoading = false,
                    episodes = popularAnimesFlow,
                )
            }
        }
    }

    private fun getAnimeRecommendations(animeId: Int) {
        animeRecommendationsUseCase(animeId = animeId)
            .asResult()
            .onEach { resource ->
                when (resource) {
                    is Result.Loading -> updateUiState { copy(isLoading = true) }
                    is Result.Success -> updateUiState {
                        copy(
                            isLoading = false,
                            recommendations = resource.data,
                        )
                    }
                    is Result.Error -> {
                        updateUiState {
                            copy(
                                isLoading = false,
                                recommendations = null,
                            )
                        }
                        triggerEvent {
                            DetailsEvents.OnError(
                                getErrorMessage(resource.exception),
                            )
                        }
                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}
