package dev.enesky.core.domain.usecase

import dev.enesky.core.domain.mappers.asAnimeRecommendation
import dev.enesky.core.domain.models.AnimeRecommendation
import dev.enesky.core.network.repository.JikanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */
class AnimeRecommendationsUseCase(
    private val jikanRepository: JikanRepository,
) {
    operator fun invoke(animeId: Int): Flow<List<AnimeRecommendation>> {
        return flow {
            val result = jikanRepository.getRecommendedAnimesByAnimeId(animeId)
            (result.getOrNull() ?: result.getOrThrow()).also {
                val data = it.data.map { recommendedAnimeResponse ->
                    recommendedAnimeResponse.asAnimeRecommendation()
                }.sortedByDescending {
                    animeRecommendation -> animeRecommendation.votes
                }
                emit(data)
            }
        }
    }
}