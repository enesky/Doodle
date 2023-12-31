package dev.enesky.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import dev.enesky.core.domain.mappers.asAnimeEpisode
import dev.enesky.core.domain.models.AnimeEpisode
import dev.enesky.core.network.repository.JikanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

class AnimeEpisodesUseCase(
    private val jikanRepository: JikanRepository,
) {
    operator fun invoke(animeId: Int): Flow<PagingData<AnimeEpisode>> {
        val response = jikanRepository.getAnimeEpisodesByAnimeId(animeId)
        val animePagingData: Flow<PagingData<AnimeEpisode>> = response.map { pagingData ->
            pagingData.map { animeResponse ->
                animeResponse.asAnimeEpisode()
            }
        }
        return animePagingData
    }
}
