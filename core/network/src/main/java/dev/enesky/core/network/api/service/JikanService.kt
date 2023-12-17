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
package dev.enesky.core.network.api.service

import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.model.AnimeFilter
import dev.enesky.core.network.model.AnimePagingResponse
import dev.enesky.core.network.model.AnimeRating
import dev.enesky.core.network.model.AnimeType
import dev.enesky.core.network.model.Character
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
        @Query("limit") limit: Int = 25,
        @Query("type") type: String = AnimeType.TV.type,
        @Query("filter") filter: String = AnimeFilter.POPULARITY.filter,
        @Query("rating") rating: String = AnimeRating.PG13.rating,
        @Query("sfw") sfw: Boolean = true,
    ): AnimePagingResponse

    @GET("anime/{anime-id}")
    suspend fun getAnimeById(
        @Path("anime-id") animeId: Int,
    ): Response<Anime>

    @GET("anime/{anime-id}/characters")
    suspend fun getCharactersByAnimeId(
        @Path("anime-id") animeId: Int,
    ): Response<List<Character>>
}
