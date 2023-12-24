package dev.enesky.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import dev.enesky.core.common.enums.AnimeFilter
import dev.enesky.core.domain.mappers.asAnime
import dev.enesky.core.domain.models.Anime
import dev.enesky.core.network.repository.JikanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Created by Enes Kamil YILMAZ on 19/12/2023
 */

class TopAnimePagingUseCase(
    private val jikanRepository: JikanRepository,
) {
    operator fun invoke(animeFilter: AnimeFilter): Flow<PagingData<Anime>> {
        return flow {
            val result = jikanRepository.getTopAnimePagingData(animeFilter)
            result.map { pagingData ->
                 pagingData.map { fullAnime ->
                    fullAnime.asAnime()
                }
            }
        }
    }
}

