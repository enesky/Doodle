package dev.enesky.doodle.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.enesky.doodle.core.network.model.Anime
import dev.enesky.doodle.core.network.util.Resource
import dev.enesky.doodle.core.network.util.asResource
import dev.enesky.doodle.feature.main.domain.usecase.AnimeCharactersUseCase
import dev.enesky.doodle.feature.main.domain.usecase.AnimeUseCase
import dev.enesky.doodle.feature.main.domain.usecase.PopularAnimesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class MainViewModel(
    private val popularAnimesUseCase: PopularAnimesUseCase,
    private val animeUseCase: AnimeUseCase,
    private val animeCharactersUseCase: AnimeCharactersUseCase
) : ViewModel() {

    var popularAnimes: Flow<PagingData<Anime>> = emptyFlow()

    init {
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
                    is Resource.Loading -> { }

                    is Resource.Success -> {
                        print(resource.data.toString())
                    }

                    is Resource.Error -> { }
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
