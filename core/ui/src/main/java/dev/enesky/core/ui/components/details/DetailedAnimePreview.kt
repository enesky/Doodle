package dev.enesky.core.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.design_system.components.DoodleImagePlaceholder
import dev.enesky.core.design_system.components.DoodleNetworkImage
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.domain.models.DetailedAnime
import dev.enesky.core.domain.models.placeholderDetailedAnime

/**
 * Created by Enes Kamil YILMAZ on 30/12/2023
 */

@Suppress("LongMethod")
@Composable
fun DetailedAnimePreview(
    modifier: Modifier = Modifier,
    detailedAnime: DetailedAnime?,
    isLoading: Boolean = false,
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Anime image
        val itemHeight = LocalConfiguration.current.screenWidthDp.dp * 1.2f
        if (isLoading || detailedAnime == null) {
            DoodleImagePlaceholder(
                modifier = Modifier
                    .height(itemHeight),
            )
        } else {
            DoodleNetworkImage(
                modifier = Modifier
                    .height(itemHeight),
                model = detailedAnime.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = detailedAnime.title,
            )
        }
        // Foreground gradient
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            DoodleTheme.colors.background,
                            DoodleTheme.colors.background.copy(alpha = 0.5f),
                            DoodleTheme.colors.transparent,
                            DoodleTheme.colors.background.copy(alpha = 0.5f),
                            DoodleTheme.colors.background,
                        ),
                    ),
                ),
        )
        // Anime title and genres
        Column(
            modifier = Modifier
                .padding(DoodleTheme.spacing.medium)
                .matchParentSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                modifier = Modifier,
                text = detailedAnime?.title ?: String.Empty,
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h3,
            )
            Text(
                modifier = Modifier,
                text = detailedAnime?.genres ?: String.Empty,
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h5,
            )
        }

        // Anime rating
        Column(
            modifier = Modifier
                .padding(DoodleTheme.spacing.medium)
                .matchParentSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                modifier = Modifier,
                text = detailedAnime?.rating ?: String.Empty,
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h6,
            )
        }

        // Detailed anime status -> Score, Rank, Popularity
        if (detailedAnime == null) return@Box
        Column(
            modifier = Modifier
                .padding(DoodleTheme.spacing.medium)
                .matchParentSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                modifier = Modifier,
                text = "Score: ${detailedAnime?.score.toString()} / 10",
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h6,
            )
            Text(
                modifier = Modifier,
                text = "Rank: ${detailedAnime?.rank.toString()}",
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h6,
            )
            Text(
                modifier = Modifier,
                text = "Popularity: ${detailedAnime?.popularity.toString()}",
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h6,
            )
        }
    }
}

@Preview
@Composable
private fun DetailedAnimePreviewPreview() {
    DoodleTheme {
        Surface(
            modifier = Modifier.size(400.dp),
            color = DoodleTheme.colors.background,
        ) {
            DetailedAnimePreview(
                detailedAnime = placeholderDetailedAnime
            )
        }
    }
}
