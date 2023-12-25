package dev.enesky.core.ui.components.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.enesky.core.common.utils.Constants
import dev.enesky.core.design_system.R
import dev.enesky.core.design_system.components.Message
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.domain.models.Anime
import dev.enesky.core.domain.models.placeholderAnime
import dev.enesky.core.domain.utils.isLoading
import dev.enesky.core.domain.utils.isNotEmpty
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Enes Kamil YILMAZ on 21/12/2023
 */

@Suppress("LongMethod", "MultipleEmitters")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeListRow(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.lorem_ipsum_medium),
    pagingItems: LazyPagingItems<Anime>?,
    onNavigateDetailsClick: (id: String) -> Unit,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        Message(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.label_no_anime_result,
        )
    },
) {
    AnimeRowTitle(Modifier, title)

    Spacer(modifier = Modifier.size(DoodleTheme.spacing.small))

    val rowState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DoodleTheme.spacing.medium),
        contentPadding = PaddingValues(
            horizontal = DoodleTheme.spacing.medium,
        ),
        state = rowState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = rowState),
    ) {
        when {
            pagingItems?.isNotEmpty() == true -> {
                items(pagingItems.itemCount) { index ->
                    pagingItems[index]?.let {
                        AnimeItem(
                            anime = it,
                            onNavigateDetailsClick = onNavigateDetailsClick,
                        )
                    } ?: PlaceholderItem()
                }
            }

            pagingItems == null
                || pagingItems.loadState.refresh.isLoading
                || pagingItems.loadState.append.isLoading -> {
                items(Constants.ITEMS_PER_PAGE) {
                    PlaceholderItem()
                }
            }

            else -> {
                item(content = emptyContent)
            }
        }
    }

    Spacer(modifier = Modifier.size(DoodleTheme.spacing.xSmall))
}

@Composable
fun AnimeRowTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = DoodleTheme.spacing.medium,
            ),
            text = title,
            color = DoodleTheme.colors.white,
            style = DoodleTheme.typography.regular.h5,
        )
        Divider(
            modifier = Modifier.padding(
                horizontal = DoodleTheme.spacing.medium,
            ),
            color = DoodleTheme.colors.white,
            thickness = 1.dp,
        )
    }
}

@Composable
fun PlaceholderItem() {
    AnimeItem(
        anime = placeholderAnime,
        isPlaceholder = true,
    )
}

@Preview
@Composable
private fun AnimeListRowPreview() {
    val animeList = listOf(placeholderAnime, placeholderAnime, placeholderAnime)
    val filledPagingData = flowOf(PagingData.from(animeList)).collectAsLazyPagingItems()
    val emptyPagingData = flowOf(PagingData.from(emptyList<Anime>())).collectAsLazyPagingItems()

    DoodleTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(DoodleTheme.spacing.medium),
        ) {
            item {
                AnimeListRow(
                    title = "Airing Now",
                    pagingItems = filledPagingData,
                    onNavigateDetailsClick = {},
                )
            }
            item {
                AnimeListRow(
                    title = "Airing Now",
                    pagingItems = emptyPagingData,
                    onNavigateDetailsClick = {},
                )
            }
            item {
                AnimeListRow(
                    title = "Most Popular",
                    pagingItems = null,
                    onNavigateDetailsClick = {},
                )
            }
        }
    }
}
