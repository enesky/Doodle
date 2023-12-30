package dev.enesky.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.design_system.components.DoodleImagePlaceholder
import dev.enesky.core.design_system.components.DoodleNetworkImage
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.domain.models.Anime
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
        )
    }
}

@Composable
private fun DetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState = DetailsUiState(),
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            DetailedAnimePreview(
                anime = uiState.anime,
                isLoading = uiState.loading,
            )
        }
        item {
            // TODO: Add anime details
        }
        item {
            // TODO: Add anime characters
        }
        item {
            // TODO: Add anime episodes
        }
        item {
            // TODO: Add anime recommendations
        }

    }
}

@Suppress("LongMethod")
@Composable
fun DetailedAnimePreview(
    modifier: Modifier = Modifier,
    anime: Anime?,
    isLoading: Boolean = false,
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val itemHeight = screenWidth * 1.2f

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        if (isLoading || anime == null) {
            DoodleImagePlaceholder(
                modifier = Modifier
                    .height(itemHeight)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                DoodleTheme.colors.transparent,
                                DoodleTheme.colors.softDark,
                            ),
                        ),
                    ),
            )
        } else {
            DoodleNetworkImage(
                modifier = Modifier
                    .height(itemHeight),
                model = anime.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = anime.title,
            )
        }
        Box(
            // Background gradient
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            DoodleTheme.colors.transparent,
                            DoodleTheme.colors.transparent,
                            DoodleTheme.colors.softDark,
                        ),
                    ),
                ),
        )
        Column(
            modifier = Modifier
                .padding(DoodleTheme.spacing.medium)
                .matchParentSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                modifier = Modifier,
                text = anime?.title ?: String.Empty,
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h3,
            )
            Text(
                modifier = Modifier,
                text = anime?.genres ?: String.Empty,
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h5,
            )
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
