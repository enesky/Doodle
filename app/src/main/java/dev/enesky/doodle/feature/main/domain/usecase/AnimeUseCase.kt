package dev.enesky.doodle.feature.main.domain.usecase

import dev.enesky.doodle.core.network.model.Anime
import dev.enesky.doodle.core.network.repository.JikanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 Created by Enes Kamil YILMAZ on 28/10/2023
*/

class AnimeUseCase(
    private val jikanRepository: JikanRepository,
) {
    operator fun invoke(animeId: Int): Flow<Anime> {
        return flow {
            val result = jikanRepository.getAnimeById(animeId)
            (result.getOrNull() ?: result.getOrThrow()).also {
                emit(it)
            }
        }
    }
}
