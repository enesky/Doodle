package dev.enesky.feature.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.design_system.components.SwipeRefresh
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.domain.models.placeholderAnimeCharacter
import dev.enesky.core.domain.models.placeholderDetailedAnime
import dev.enesky.core.ui.components.details.AnimeCharactersRow
import dev.enesky.core.ui.components.details.AnimeRecommendationsRow
import dev.enesky.core.ui.components.details.DetailedAnimePreview
import dev.enesky.core.ui.components.details.DetailedAnimeSummary
import dev.enesky.feature.details.helpers.DetailsUiState
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun DetailsRoute(
    modifier: Modifier = Modifier,
    animeId: String,
    viewModel: DetailsViewModel = koinViewModel(),
) {
    LaunchedEffect(animeId) {
        viewModel.getThemAll(animeId = animeId.toInt())
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreen(
        modifier = modifier,
        uiState = uiState,
    ) {
        viewModel.getThemAll(animeId = animeId.toInt())
    }
}

@Composable
private fun DetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState = DetailsUiState(),
    onRefresh: () -> Unit = {},
) {
    SwipeRefresh(
        modifier = modifier,
        isRefreshing = uiState.loading,
        onRefresh = onRefresh,
    ) {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(
                top = DoodleTheme.spacing.statusBarPadding,
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(),
            ),
            state = listState,
        ) {
            item {
                DetailedAnimePreview(
                    detailedAnime = uiState.detailedAnime,
                    isLoading = uiState.loading,
                )
            }
            item {
                DetailedAnimeSummary(
                    summary = uiState.detailedAnime?.summary ?: String.Empty,
                    isLoading = uiState.loading,
                )
            }
            item {
                AnimeCharactersRow(
                    animeCharacters = uiState.characters,
                    isLoading = uiState.loading,
                )
            }
            item {
                // TODO: Add anime episodes
            }
            item {
                AnimeRecommendationsRow(
                    animeRecommendations = uiState.recommendations,
                    isLoading = uiState.loading,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    DoodleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DoodleTheme.colors.background,
        ) {
            DetailsScreen(
                uiState = DetailsUiState(
                    detailedAnime = placeholderDetailedAnime,
                    characters = List(5) { placeholderAnimeCharacter },
                ),
            )
        }
    }
}
