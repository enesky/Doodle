package dev.enesky.doodle.core.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.enesky.doodle.core.network.api.service.JikanService
import dev.enesky.doodle.core.network.util.getBodyOrThrowError
import dev.enesky.doodle.core.network.model.Anime
import dev.enesky.doodle.core.network.model.Character
import dev.enesky.doodle.core.network.paging.PopularAnimesPagingSource
import dev.enesky.doodle.core.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class JikanDataSourceImpl(
    private val jikanService: JikanService
) : JikanDataSource {

    override fun getPopularAnimes(): Flow<PagingData<Anime>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                PopularAnimesPagingSource(jikanService)
            }
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
