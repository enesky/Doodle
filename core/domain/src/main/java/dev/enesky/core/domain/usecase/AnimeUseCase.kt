package dev.enesky.core.domain.usecase

import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.repository.JikanRepository
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
