package dev.enesky.feature.home.helpers

import androidx.paging.PagingData
import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.network.model.MiniAnime
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

data class HomeUiState(
    override val loading: Boolean = false,
    val airingAnimes: Flow<PagingData<MiniAnime>>? = null,
    val upcomingAnimes: Flow<PagingData<MiniAnime>>? = null,
    val popularAnimes: Flow<PagingData<MiniAnime>>? = null,
    val favoriteAnimes: Flow<PagingData<MiniAnime>>? = null,
    val selectedAnime: MiniAnime? = null,
    val animeCharacters: List<MiniAnime>? = null,
) : IUiState
