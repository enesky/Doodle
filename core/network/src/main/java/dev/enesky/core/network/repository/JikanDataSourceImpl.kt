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

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.enesky.core.common.utils.Constants.ITEMS_PER_PAGE
import dev.enesky.core.common.enums.AnimeFilter
import dev.enesky.core.data.models.Character
import dev.enesky.core.data.response.AnimeResponse
import dev.enesky.core.data.response.FullAnime
import dev.enesky.core.data.response.base.BaseResponse
import dev.enesky.core.network.api.JikanService
import dev.enesky.core.network.paging.TopAnimePagingSource
import dev.enesky.core.network.util.getBodyOrThrowError
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class JikanDataSourceImpl(
    private val jikanService: JikanService,
) : JikanDataSource {

    override fun getTopAnimePagingData(animeFilter: AnimeFilter): Flow<PagingData<AnimeResponse>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                TopAnimePagingSource(jikanService, animeFilter)
            },
        ).flow
    }

    override suspend fun getAnimeById(animeId: Int): Result<BaseResponse<FullAnime>> {
        return kotlin.runCatching {
            jikanService.getAnimeById(animeId).getBodyOrThrowError()
        }
    }

    override suspend fun getCharactersByAnimeId(animeId: Int): Result<List<Character>> {
        return kotlin.runCatching {
            jikanService.getCharactersByAnimeId(animeId).getBodyOrThrowError()
        }
    }
}
