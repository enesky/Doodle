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

import androidx.paging.PagingData
import dev.enesky.core.network.model.Anime
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

/**
 * Use this approach if the usecase doesn't have a logic inside
 *
 * Check: https://betterprogramming.pub/how-to-avoid-use-cases-boilerplate-in-android-d0c9aa27ef27
*/
fun interface PopularAnimesUseCase : () -> Flow<PagingData<Anime>>

/**
 * Use this approach if the usecase has a logic inside
 *
 * class PopularAnimesUseCase(
 *     private val jikanRepository: JikanRepository,
 * ) {
 *     operator fun invoke(): Flow<PagingData<Anime>> {
 *         return jikanRepository.getPopularAnimes()
 *     }
 * }
 */
