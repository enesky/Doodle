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
