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
import dev.enesky.core.data.models.AnimeCharacterResponse
import dev.enesky.core.data.models.AnimeResponse
import dev.enesky.core.data.models.FullAnime
import dev.enesky.core.data.base.BaseResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

interface JikanDataSource {

    fun getTopAnimePagingData(animeFilter: AnimeFilter): Flow<PagingData<AnimeResponse>>
    suspend fun getAnimeById(animeId: Int): Result<BaseResponse<FullAnime>>
    suspend fun getCharactersByAnimeId(animeId: Int): Result<List<AnimeCharacterResponse>>
}
