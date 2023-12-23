package dev.enesky.core.design_system.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.data.models.ImageList
import dev.enesky.core.data.models.Images
import dev.enesky.core.design_system.common.DoodleImagePlaceholder
import dev.enesky.core.design_system.common.DoodleNetworkImage
import dev.enesky.core.design_system.common.placeholder
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.design_system.theme.Icons
import dev.enesky.core.domain.models.Anime

/**
 * Created by Enes Kamil YILMAZ on 21/12/2023
 */

@Suppress("LongMethod")
@Composable
fun AnimeItem(
    modifier: Modifier = Modifier,
    anime: Anime,
    isPlaceholder: Boolean = false,
    onNavigateDetailsClick: ((id: String) -> Unit)? = null,
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val itemWidth = screenWidth / 3.5f
    val itemHeight = itemWidth * 1.5f

    Column(
        modifier = if (isPlaceholder) {
            modifier
        } else {
            modifier.clickable {
                onNavigateDetailsClick?.invoke(anime.id.toString())
            }
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (isPlaceholder) {
            DoodleImagePlaceholder(
                modifier = Modifier
                    .size(itemWidth, itemHeight)
                    .clip(DoodleTheme.shapes.small),
            )
        } else {
            DoodleNetworkImage(
                modifier = Modifier
                    .size(itemWidth, itemHeight)
                    .clip(DoodleTheme.shapes.small),
                model = anime.images.jpg?.imageUrl,
                contentDescription = anime.title,
            )
        }

        val textWeight = 7f
        val iconWeight = 1f

        Row(
            modifier = if (isPlaceholder) {
                Modifier
                    .width(itemWidth)
                    .placeholder(color = DoodleTheme.colors.softDark)
            } else {
                Modifier
                    .width(itemWidth)
            },
        ) {
            Column(
                modifier = Modifier
                    .weight(textWeight),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    modifier = Modifier,
                    text = anime.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = DoodleTheme.typography.regular.h6,
                )
                Text(
                    modifier = Modifier,
                    text = anime.genres,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = DoodleTheme.typography.regular.h7,
                )
            }
            if (isPlaceholder.not()) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(
                            top = DoodleTheme.spacing.xSmall,
                        )
                        .weight(iconWeight),
                    imageVector = Icons.MoreVert,
                    tint = DoodleTheme.colors.white,
                    contentDescription = anime.title + "options",
                )
            }
        }
    }
}

@Preview
@Composable
private fun AnimeItemPreview() {
    DoodleTheme {
        AnimeItem(
            anime = Anime(
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
            isPlaceholder = false,
        )
    }
}
