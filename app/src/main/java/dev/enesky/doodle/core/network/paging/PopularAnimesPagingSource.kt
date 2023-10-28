package dev.enesky.doodle.core.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.enesky.doodle.core.network.api.service.JikanService
import dev.enesky.doodle.core.network.model.Anime
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class PopularAnimesPagingSource(
    private val jikanService: JikanService
) : PagingSource<Int, Anime>() {

    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        return try {
            val nextPage = params.key ?: 1
            val popularAnimes = jikanService.getPopularAnimes(nextPage)
            LoadResult.Page(
                data = popularAnimes.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey =
                    if (popularAnimes.data.isEmpty() || popularAnimes.pagination.hasNextPage.not()) {
                        null
                    } else {
                        popularAnimes.pagination.currentPage.plus(1)
                    }
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}
