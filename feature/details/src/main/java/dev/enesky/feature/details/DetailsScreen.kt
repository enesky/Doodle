package dev.enesky.feature.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.components.home.AnimePreview
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(animeId) {
        viewModel.getThemAll(animeId = animeId.toInt())
    }

    DetailsScreen(
        modifier = modifier,
        uiState = uiState,
    )
}

@Composable
private fun DetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState = DetailsUiState(),
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AnimePreview(
                anime = uiState.anime,
                isLoading = uiState.loading,
            )
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    DoodleTheme {
        DetailsScreen()
    }
}
