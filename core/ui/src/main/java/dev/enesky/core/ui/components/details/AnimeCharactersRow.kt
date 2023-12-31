package dev.enesky.core.ui.components.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.common.utils.Constants
import dev.enesky.core.design_system.R
import dev.enesky.core.design_system.components.DoodleImagePlaceholder
import dev.enesky.core.design_system.components.DoodleNetworkImage
import dev.enesky.core.design_system.components.Message
import dev.enesky.core.design_system.components.placeholder
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.domain.models.AnimeCharacter
import dev.enesky.core.domain.models.placeholderAnimeCharacter
import dev.enesky.core.ui.components.home.TitleRow

/**
 * Created by Enes Kamil YILMAZ on 30/12/2023
 */

@Suppress("LongMethod", "MultipleEmitters", "ImmutableCollections", "UnstableCollections")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeCharactersRow(
    modifier: Modifier = Modifier,
    animeCharacters: List<AnimeCharacter>? = listOf(placeholderAnimeCharacter, placeholderAnimeCharacter),
    isLoading: Boolean = false,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        val width = LocalConfiguration.current.screenWidthDp.dp - DoodleTheme.spacing.medium * 2
        val height = width * 0.5f
        Message(
            modifier = Modifier.size(width, height),
            messageResourceId = R.string.label_no_characters_result,
        )
    },
) {
    Spacer(modifier = modifier.size(DoodleTheme.spacing.xxSmall))

    TitleRow(
        modifier = Modifier,
        title = "Characters",
    )

    Spacer(modifier = Modifier.size(DoodleTheme.spacing.small))

    val rowState = rememberLazyListState()
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(DoodleTheme.spacing.small),
        contentPadding = PaddingValues(
            horizontal = DoodleTheme.spacing.medium,
        ),
        state = rowState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = rowState),
    ) {
        when {
            isLoading -> {
                items(Constants.ITEMS_PER_PAGE) {
                    AnimeCharacterPlaceholder()
                }
            }

            !animeCharacters.isNullOrEmpty() -> {
                items(animeCharacters.count()) { index ->
                    AnimeCharacterItem(
                        animeCharacter = animeCharacters[index],
                    )
                }
            }

            else -> {
                item(content = emptyContent)
            }
        }
    }

    Spacer(modifier = Modifier.size(DoodleTheme.spacing.xxSmall))
}

@Suppress("LongMethod")
@Composable
fun AnimeCharacterItem(
    modifier: Modifier = Modifier,
    animeCharacter: AnimeCharacter,
    isPlaceholder: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Anime Character image
        val itemWidth = LocalConfiguration.current.screenWidthDp.dp / 4f
        val itemHeight = itemWidth * 1.5f
        if (isPlaceholder) {
            DoodleImagePlaceholder(
                modifier = Modifier
                    .size(itemWidth, itemHeight)
                    .clip(DoodleTheme.shapes.extraSmall),
            )
        } else {
            DoodleNetworkImage(
                modifier = Modifier
                    .size(itemWidth, itemHeight)
                    .clip(DoodleTheme.shapes.extraSmall),
                model = animeCharacter.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = animeCharacter.name,
            )
        }

        Spacer(modifier = Modifier.size(DoodleTheme.spacing.xxSmall))

        // Anime character name, role
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
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    modifier = Modifier,
                    text = animeCharacter.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = DoodleTheme.typography.regular.h6,
                    color = DoodleTheme.colors.white,
                )
                Text(
                    modifier = Modifier,
                    text = animeCharacter.role,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = DoodleTheme.typography.regular.h7,
                    color = DoodleTheme.colors.white,
                )
            }
        }
    }
}

@Composable
fun AnimeCharacterPlaceholder() {
    AnimeCharacterItem(
        animeCharacter = placeholderAnimeCharacter,
        isPlaceholder = true,
    )
}

@Preview
@Composable
private fun AnimeCharactersRowPreview() {
    val animeCharacters = List(5) { placeholderAnimeCharacter }
    val emptyAnimeCharacters = listOf<AnimeCharacter>()

    DoodleTheme {
        Column(
            modifier = Modifier.wrapContentHeight(),
        ) {
            AnimeCharactersRow(
                animeCharacters = animeCharacters,
            )
            AnimeCharactersRow(
                isLoading = true,
            )
            AnimeCharactersRow(
                animeCharacters = emptyAnimeCharacters,
            )
        }
    }
}
