package dev.enesky.feature.details.helpers

import androidx.paging.PagingData
import dev.enesky.core.common.delegate.IUiState
import dev.enesky.core.domain.models.AnimeCharacter
import dev.enesky.core.domain.models.AnimeEpisode
import dev.enesky.core.domain.models.AnimeRecommendation
import dev.enesky.core.domain.models.DetailedAnime
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 07/12/2023
 */

data class DetailsUiState(
    override val isLoading: Boolean = false,
    val detailedAnime: DetailedAnime? = null,
    val characters: List<AnimeCharacter>? = null,
    val episodes: Flow<PagingData<AnimeEpisode>>? = null,
    val recommendations: List<AnimeRecommendation>? = null,
) : IUiState
