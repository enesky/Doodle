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
package dev.enesky.core.network.api

import dev.enesky.core.common.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

interface JikanService {

    @GET("top/anime")
    suspend fun getTopAnimePaging(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = Constants.ITEMS_PER_PAGE,
        @Query("type") type: String = dev.enesky.core.data.AnimeType.TV.type,
        @Query("filter") filter: String = dev.enesky.core.data.AnimeFilter.POPULARITY.filter,
        @Query("rating") rating: String = dev.enesky.core.data.AnimeRating.PG13.rating,
        @Query("sfw") sfw: Boolean = true,
    ): dev.enesky.core.data.AnimePagingResponse

    @GET("anime/{id}/full")
    suspend fun getAnimeById(
        @Path("id") animeId: Int,
    ): Response<dev.enesky.core.data.BaseResponse<dev.enesky.core.data.FullAnime>>

    @GET("anime/{anime-id}/characters")
    suspend fun getCharactersByAnimeId(
        @Path("anime-id") animeId: Int,
    ): Response<List<dev.enesky.core.data.Character>>
}
