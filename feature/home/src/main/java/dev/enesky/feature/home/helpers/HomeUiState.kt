package dev.enesky.feature.home.helpers

import androidx.paging.PagingData
import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.domain.models.Anime
import dev.enesky.core.domain.models.DetailedAnime
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

data class HomeUiState(
    override val isLoading: Boolean = false,
    val airingAnimes: Flow<PagingData<Anime>>? = null,
    val upcomingAnimes: Flow<PagingData<Anime>>? = null,
    val popularAnimes: Flow<PagingData<Anime>>? = null,
    val favoriteAnimes: Flow<PagingData<Anime>>? = null,
    val selectedAnime: Anime? = null,
    val previewAnime: DetailedAnime? = null,
) : IUiState
