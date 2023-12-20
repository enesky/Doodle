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
package dev.enesky.core.network.repository

import androidx.paging.PagingData
import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.model.AnimeFilter
import dev.enesky.core.network.model.BaseResponse
import dev.enesky.core.network.model.Character
import dev.enesky.core.network.model.FullAnime
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class JikanRepository(
    private val jikanDataSource: JikanDataSource,
) {

    fun getTopAnimePagingData(animeFilter: AnimeFilter): Flow<PagingData<Anime>> {
        return jikanDataSource.getTopAnimePagingData(animeFilter)
    }

    suspend fun getAnimeById(animeId: Int): Result<BaseResponse<FullAnime>> {
        return jikanDataSource.getAnimeById(animeId)
    }

    suspend fun getCharactersByAnimeId(animeId: Int): Result<List<Character>> {
        return jikanDataSource.getCharactersByAnimeId(animeId)
    }
}
