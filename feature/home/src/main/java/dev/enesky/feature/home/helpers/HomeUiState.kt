package dev.enesky.feature.home.helpers

import androidx.paging.PagingData
import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.network.model.Anime
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

data class HomeUiState(
    override val loading: Boolean = false,
    val popularAnimes: Flow<PagingData<Anime>>? = null,
    val selectedAnime: Anime? = null,
    val animeCharacters: List<Anime>? = null,
) : IUiState
