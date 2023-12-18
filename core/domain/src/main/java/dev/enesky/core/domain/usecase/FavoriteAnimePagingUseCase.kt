package dev.enesky.core.domain.usecase

import androidx.paging.PagingData
import dev.enesky.core.network.model.Anime
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 18/12/2023
 */
fun interface FavoriteAnimePagingUseCase : () -> Flow<PagingData<Anime>>
