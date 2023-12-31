package dev.enesky.core.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.enesky.core.data.models.AnimeEpisodeResponse
import dev.enesky.core.network.api.JikanService
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */
class AnimeEpisodesPagingSource(
    private val jikanService: JikanService,
    private val animeId: Int,
) : PagingSource<Int, AnimeEpisodeResponse>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeEpisodeResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeEpisodeResponse> {
        return try {
            val nextPage = params.key ?: 1
            val response = jikanService.getEpisodesByAnimeId(
                page = nextPage,
                animeId = animeId,
            )
            LoadResult.Page(
                data = response.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey =
                if (response?.data == null ||
                    response.data.isEmpty() ||
                    response.pagination.hasNextPage.not()
                ) {
                    null
                } else {
                    response.pagination.currentPage.plus(1)
                },
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
