package dev.enesky.feature.details

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.design_system.components.SwipeRefresh
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.domain.models.placeholderAnimeCharacter
import dev.enesky.core.domain.models.placeholderDetailedAnime
import dev.enesky.core.ui.components.details.AnimeCharactersRow
import dev.enesky.core.ui.components.details.DetailedAnimePreview
import dev.enesky.core.ui.components.home.TitleRow
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
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(
                vertical = DoodleTheme.spacing.xSmall,
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
                // TODO: Add anime details
                DetailedAnimeSummary(
                    summary = uiState.detailedAnime?.summary ?: String.Empty,
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
                // TODO: Add anime recommendations
            }
        }
    }
}

@Composable
fun DetailedAnimeSummary(
    modifier: Modifier = Modifier,
    summary: String,
) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    var maxLines = if (expanded) Int.MAX_VALUE else 7

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TitleRow(modifier, "Summary")

        Box {
            Text(
                modifier = Modifier
                    .padding(
                        start = DoodleTheme.spacing.medium,
                        end = DoodleTheme.spacing.medium,
                        top = DoodleTheme.spacing.medium,
                        bottom = DoodleTheme.spacing.large,
                    )
                    .animateContentSize(),
                text = summary,
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.regular.h6,
                maxLines = maxLines,
            )

            // Add gradient to the bottom of the first paragraph
            // Foreground gradient
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                DoodleTheme.colors.transparent,
                                DoodleTheme.colors.transparent,
                                DoodleTheme.colors.transparent,
                                DoodleTheme.colors.background.copy(alpha = 0.2f),
                                DoodleTheme.colors.background,
                            ),
                        ),
                    )
                    .clickable {
                        expanded = !expanded
                    },
                contentAlignment = Alignment.BottomCenter,
            ) {
                Column {
                    Spacer(modifier = Modifier.height(DoodleTheme.spacing.xLarge))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier,
                            text = if (expanded) "Show less" else "Show more",
                            color = DoodleTheme.colors.white,
                            style = DoodleTheme.typography.bold.h6.copy(
                                textAlign = TextAlign.Center,
                            ),
                        )
                        // Rotate the arrow icon by 180 degrees when expanded
                        val rotationAngle by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f,
                            animationSpec = tween(
                                durationMillis = 500,
                            ),
                            label = "",
                        )
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer {
                                    rotationZ = rotationAngle
                                },
                            painter = rememberVectorPainter(image = Icons.Default.KeyboardArrowDown),
                            tint = DoodleTheme.colors.white,
                            contentDescription = ""
                        )
                    }
                }
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
