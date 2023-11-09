package dev.enesky.core.network.api.service

import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.model.AnimeFilter
import dev.enesky.core.network.model.AnimePagingResponse
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
    suspend fun getPopularAnimes(
        @Query("page") page: Int = 1,
        @Query("type") type: String = AnimeType.TV.type,
        @Query("filter") filter: String = AnimeFilter.G.filter,
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
