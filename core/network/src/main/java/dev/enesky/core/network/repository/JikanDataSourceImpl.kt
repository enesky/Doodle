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
import dev.enesky.core.network.api.service.JikanService
import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.model.Character
import dev.enesky.core.network.paging.PopularAnimesPagingSource
import dev.enesky.core.network.util.Constants.ITEMS_PER_PAGE
import dev.enesky.core.network.util.getBodyOrThrowError
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class JikanDataSourceImpl(
    private val jikanService: JikanService,
) : JikanDataSource {

    override fun getPopularAnimes(): Flow<PagingData<Anime>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                PopularAnimesPagingSource(jikanService)
            },
        ).flow
    }

    override suspend fun getAnimeById(animeId: Int): Result<Anime> {
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
