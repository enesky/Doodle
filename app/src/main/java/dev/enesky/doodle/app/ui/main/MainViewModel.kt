/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.enesky.doodle.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.enesky.core.domain.usecase.AnimeCharactersUseCase
import dev.enesky.core.domain.usecase.AnimeUseCase
import dev.enesky.core.domain.usecase.PopularAnimesUseCase
import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.util.Resource
import dev.enesky.core.network.util.asResource
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
    private val animeCharactersUseCase: AnimeCharactersUseCase,
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
