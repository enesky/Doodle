package dev.enesky.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.usecase.AnimeCharactersUseCase
import dev.enesky.core.domain.usecase.AnimeUseCase
import dev.enesky.core.domain.usecase.PopularAnimesUseCase
import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.util.Resource
import dev.enesky.core.network.util.asResource
import dev.enesky.feature.main.helpers.HomeEvents
import dev.enesky.feature.main.helpers.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class HomeViewModel(
    private val popularAnimesUseCase: PopularAnimesUseCase,
    private val animeUseCase: AnimeUseCase,
    private val animeCharactersUseCase: AnimeCharactersUseCase,
) : ViewModel(),
    UiState<HomeUiState> by UiStateDelegate(initialState = { HomeUiState() }),
    Event<HomeEvents> by EventDelegate() {

    var popularAnimes: Flow<PagingData<Anime>> = emptyFlow()

    init {
        println("@@@@@ HomeViewModel initialized")

        getAnimeById(1)
        getAnimeCharacters(1)
    }

    fun getPopularAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            popularAnimes = popularAnimesUseCase().cachedIn(viewModelScope)
        }
    }

    fun getAnimeById(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase(animeId).asResource().onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        print(resource.data.toString())
                    }

                    is Resource.Error -> {}
                }
            }.launchIn(this)
        }
    }

    fun getAnimeCharacters(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = animeCharactersUseCase(animeId)
            if (result.isSuccess) {
                print(result.getOrThrow())
            }
        }
    }
}
