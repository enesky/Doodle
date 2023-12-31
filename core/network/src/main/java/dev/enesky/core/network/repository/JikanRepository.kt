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
import dev.enesky.core.common.enums.AnimeFilter
import dev.enesky.core.data.base.BaseResponse
import dev.enesky.core.data.models.AnimeCharacterResponse
import dev.enesky.core.data.models.AnimeEpisodeResponse
import dev.enesky.core.data.models.AnimeResponse
import dev.enesky.core.data.models.DetailedAnimeResponse
import dev.enesky.core.data.models.RecommendedAnimeResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class JikanRepository(
    private val jikanDataSource: JikanDataSource,
) {

    fun getTopAnimePagingData(animeFilter: AnimeFilter): Flow<PagingData<AnimeResponse>> {
        return jikanDataSource.getTopAnimePagingData(animeFilter)
    }

    suspend fun getAnimeById(animeId: Int): Result<BaseResponse<DetailedAnimeResponse>> {
        return jikanDataSource.getAnimeById(animeId)
    }

    suspend fun getCharactersByAnimeId(animeId: Int): Result<BaseResponse<List<AnimeCharacterResponse>>> {
        return jikanDataSource.getCharactersByAnimeId(animeId)
    }

    suspend fun getRecommendedAnimesByAnimeId(animeId: Int): Result<BaseResponse<List<RecommendedAnimeResponse>>> {
        return jikanDataSource.getRecommendedAnimeByAnimeId(animeId)
    }

    fun getAnimeEpisodesByAnimeId(animeId: Int): Flow<PagingData<AnimeEpisodeResponse>> {
        return jikanDataSource.getAnimeEpisodesByAnimeId(animeId)
    }
}
