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
package dev.enesky.core.domain.usecase

import dev.enesky.core.domain.mappers.asDetailedAnime
import dev.enesky.core.domain.models.DetailedAnime
import dev.enesky.core.network.repository.JikanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 Created by Enes Kamil YILMAZ on 28/10/2023
*/

class DetailedAnimeUseCase(
    private val jikanRepository: JikanRepository,
) {
    operator fun invoke(animeId: Int): Flow<DetailedAnime> {
        return flow {
            val result = jikanRepository.getAnimeById(animeId)
            (result.getOrNull() ?: result.getOrThrow()).also {
                emit(it.data.asDetailedAnime())
            }
        }
    }
}
