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
import dev.enesky.core.domain.models.Anime
import dev.enesky.core.domain.models.placeholderAnime

/**
 * Created by Enes Kamil YILMAZ on 30/12/2023
 */

@Suppress("LongMethod")
@Composable
fun DetailedAnimePreview(
    modifier: Modifier = Modifier,
    anime: Anime?,
    isLoading: Boolean = false,
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Anime image
        val itemHeight = LocalConfiguration.current.screenWidthDp.dp * 1.2f
        if (isLoading || anime == null) {
            DoodleImagePlaceholder(
                modifier = Modifier
                    .height(itemHeight),
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
private fun DetailedAnimePreviewPreview() {
    DoodleTheme {
        Surface(
            modifier = Modifier.size(400.dp),
            color = DoodleTheme.colors.background,
        ) {
            DetailedAnimePreview(
                anime = placeholderAnime
            )
        }
    }
}
