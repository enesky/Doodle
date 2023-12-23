package dev.enesky.feature.home.helpers

import androidx.paging.PagingData
import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.data.FullAnime
import dev.enesky.core.data.MiniAnime
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

data class DetailsUiState(
    override val loading: Boolean = false,
    override var errorMessage: String? = null,
    val anime: FullAnime? = null,
) : IUiState {



}
