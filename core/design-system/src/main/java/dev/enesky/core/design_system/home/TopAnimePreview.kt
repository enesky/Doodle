package dev.enesky.core.design_system.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.data.ImageList
import dev.enesky.core.data.Images
import dev.enesky.core.data.MiniAnime
import dev.enesky.core.design_system.R
import dev.enesky.core.design_system.common.DoodleImagePlaceholder
import dev.enesky.core.design_system.common.DoodleNetworkImage
import dev.enesky.core.design_system.theme.DoodleTheme

/**
 * Created by Enes Kamil YILMAZ on 20/12/2023
 */

@Suppress("LongMethod")
@Composable
fun TopAnimePreview(
    modifier: Modifier = Modifier,
    anime: MiniAnime?,
    isLoading: Boolean = false,
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val itemHeight = screenWidth * 0.75f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                all = DoodleTheme.spacing.medium,
            )
            .clip(DoodleTheme.shapes.small)
            .also {
                if (anime != null) {
                    it.clickable {
                    }
                }
            },
    ) {
        if (isLoading || anime == null || anime?.images?.jpg?.imageUrl == null) {
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
                model = anime.images.jpg?.imageUrl,
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
                text = anime?.title
                    ?: stringResource(id = R.string.lorem_ipsum_short),
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h3,
            )
            Text(
                modifier = Modifier,
                text = anime?.genres ?: stringResource(id = R.string.lorem_ipsum_medium),
                color = DoodleTheme.colors.white,
                style = DoodleTheme.typography.bold.h5,
            )
        }
    }
}

@Preview
@Composable
private fun TopAnimePreviewPreview() {
    DoodleTheme {
        TopAnimePreview(
            anime = MiniAnime(
                id = 0,
                title = "Jujutsu Kaisen",
                genres = "Action | Adventure",
                trailer = null,
                url = "",
                images = Images(
                    jpg = ImageList(
                        imageUrl = "",
                    ),
                ),
            ),
        )
    }
}