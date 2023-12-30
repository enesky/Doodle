package dev.enesky.feature.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.design_system.components.SwipeRefresh
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.components.details.AnimeCharactersRow
import dev.enesky.core.ui.components.details.DetailedAnimePreview
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

    Surface(
        modifier = modifier.fillMaxSize(),
        color = DoodleTheme.colors.background,
    ) {
        DetailsScreen(
            modifier = modifier,
            uiState = uiState,
            onRefresh = {
                viewModel.getThemAll(animeId = animeId.toInt())
            },
        )
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(
                vertical = DoodleTheme.spacing.xSmall,
            ),
        ) {
            item {
                DetailedAnimePreview(
                    detailedAnime = uiState.detailedAnime,
                    isLoading = uiState.loading,
                )
            }
            item {
                // TODO: Add anime details
            }
            item {
                AnimeCharactersRow(
                    title = "Characters",
                    animeCharacters = uiState.characters,
                    isLoading = uiState.loading,
                )
            }
            item {
                // TODO: Add anime episodes
            }
            item {
                // TODO: Add anime recommendations
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
            DetailsScreen()
        }
    }
}
