package dev.enesky.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.usecase.AnimeCharactersUseCase
import dev.enesky.core.domain.usecase.AnimeUseCase
import dev.enesky.core.network.util.Resource
import dev.enesky.core.network.util.asResource
import dev.enesky.feature.home.helpers.DetailsEvents
import dev.enesky.feature.details.helpers.DetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class DetailsViewModel(
    val animeUseCase: AnimeUseCase,
    val animeCharactersUseCase: AnimeCharactersUseCase
) : ViewModel(),
    UiState<DetailsUiState> by UiStateDelegate(initialState = { DetailsUiState() }),
    Event<DetailsEvents> by EventDelegate() {

    init {
        getAnime()
    }

    private fun getAnime() {
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
                                    anime = null,
                                )
                            }
                        }

                        is Resource.Success -> {
                            if (resource.data.id == 0) {
                                updateUiState {
                                    copy(
                                        loading = false,
                                        anime = null,
                                    )
                                }
                                return@onEach
                            }
                            updateUiState {
                                copy(
                                    loading = false,
                                    anime = resource.data
                                )
                            }
                        }

                        is Resource.Error -> {
                            updateUiState {
                                copy(
                                    loading = false,
                                    anime = null,
                                )
                            }
                        }
                    }
                }.launchIn(this)
        }
    }

}
