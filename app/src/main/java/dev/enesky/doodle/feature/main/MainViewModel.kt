package dev.enesky.doodle.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.enesky.doodle.core.network.model.Anime
import dev.enesky.doodle.feature.main.domain.usecase.AnimeUseCase
import dev.enesky.doodle.feature.main.domain.usecase.PopularAnimesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class MainViewModel(
    private val popularAnimesUseCase: PopularAnimesUseCase,
    private val animeUseCase: AnimeUseCase
): ViewModel() {

    private var popularAnimes: Flow<PagingData<Anime>> = emptyFlow()

    init {
        getAnimeById(1)
    }

    fun getPopularAnimes(): String {
        viewModelScope.launch(Dispatchers.IO) {
            popularAnimes = popularAnimesUseCase().cachedIn(viewModelScope)
        }
        return popularAnimes.toString()
    }

    fun getAnimeById(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            animeUseCase(animeId)
        }
    }

}
