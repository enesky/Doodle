package dev.enesky.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.common.result.Result
import dev.enesky.core.common.result.asResult
import dev.enesky.core.domain.usecase.AnimeCharactersUseCase
import dev.enesky.core.domain.usecase.AnimeUseCase
import dev.enesky.feature.details.helpers.DetailsEvents
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
    val animeCharactersUseCase: AnimeCharactersUseCase,
) : ViewModel(),
    UiState<DetailsUiState> by UiStateDelegate(initialState = { DetailsUiState() }),
    Event<DetailsEvents> by EventDelegate() {

    fun getThemAll(animeId: Int) {
        getAnime(animeId)
        getAnimeCharacters(animeId)
    }

    private fun getAnime(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase(animeId = animeId)
                .asResult()
                .onEach { resource ->
                    updateUiState {
                        when(resource) {
                            is Result.Loading -> copy(loading = true)
                            is Result.Success -> copy(
                                loading = false,
                                anime = resource.data
                            )
                            is Result.Error -> copy(
                                loading = false,
                                anime = null,
                                errorMessage = resource.exception?.message
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun getAnimeCharacters(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            animeCharactersUseCase(animeId = animeId)
                .asResult()
                .onEach { resource ->
                    updateUiState {
                        when(resource) {
                            is Result.Loading -> copy(loading = true)
                            is Result.Success -> copy(
                                loading = false,
                                characters = resource.data
                            )
                            is Result.Error -> copy(
                                loading = false,
                                characters = null,
                                errorMessage = resource.exception?.message
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

}
