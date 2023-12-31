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
package dev.enesky.core.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.enesky.core.common.enums.AnimeFilter
import dev.enesky.core.data.models.AnimeResponse
import dev.enesky.core.network.api.JikanService
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

class TopAnimePagingSource(
    private val jikanService: JikanService,
    private val animeFilter: AnimeFilter = AnimeFilter.POPULARITY,
) : PagingSource<Int, AnimeResponse>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeResponse> {
        return try {
            val nextPage = params.key ?: 1
            val animePagingResponse = jikanService.getTopAnimePaging(
                page = nextPage,
                filter = animeFilter.filter,
            )
            LoadResult.Page(
                data = animePagingResponse.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey =
                if (animePagingResponse?.data == null ||
                    animePagingResponse.data.isEmpty() ||
                    animePagingResponse.pagination.hasNextPage.not()
                ) {
                    null
                } else {
                    animePagingResponse.pagination.currentPage.plus(1)
                },
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
