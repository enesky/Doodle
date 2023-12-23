package dev.enesky.feature.details.helpers

import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.data.response.FullAnime

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

data class DetailsUiState(
    override val loading: Boolean = false,
    override var errorMessage: String? = null,
    val anime: FullAnime? = null,
) : IUiState {



}
