package dev.enesky.core.domain.usecase

import androidx.paging.PagingData
import dev.enesky.core.network.model.Anime
import dev.enesky.core.network.model.AnimeFilter
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 19/12/2023
 */

fun interface TopAnimePagingUseCase : (AnimeFilter) -> Flow<PagingData<Anime>>
