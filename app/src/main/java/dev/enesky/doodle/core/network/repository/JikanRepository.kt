package dev.enesky.doodle.core.network.repository

import androidx.paging.PagingData
import dev.enesky.doodle.core.network.model.Anime
import dev.enesky.doodle.core.network.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class JikanRepository(
    private val jikanDataSource: JikanDataSource
) {

    fun getPopularAnimes(): Flow<PagingData<Anime>> {
        return jikanDataSource.getPopularAnimes()
    }

    suspend fun getAnimeById(animeId: Int): Result<Anime> {
        return jikanDataSource.getAnimeById(animeId)
    }

    suspend fun getCharactersByAnimeId(animeId: Int): Result<List<Character>> {
        return jikanDataSource.getCharactersByAnimeId(animeId)
    }

}
