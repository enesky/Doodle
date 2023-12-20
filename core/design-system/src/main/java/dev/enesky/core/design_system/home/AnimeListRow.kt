package dev.enesky.core.design_system.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.paging.compose.LazyPagingItems
import dev.enesky.core.common.utils.Constants
import dev.enesky.core.data.ImageList
import dev.enesky.core.data.Images
import dev.enesky.core.data.MiniAnime
import dev.enesky.core.design_system.R
import dev.enesky.core.design_system.common.Error
import dev.enesky.core.design_system.common.Message
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.util.error
import dev.enesky.core.ui.util.isEmpty
import dev.enesky.core.ui.util.isError
import dev.enesky.core.ui.util.isFinished
import dev.enesky.core.ui.util.isLoading
import dev.enesky.core.ui.util.isNotEmpty

/**
 * Created by Enes Kamil YILMAZ on 21/12/2023
 */

@Suppress("LongMethod", "MultipleEmitters")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeListRow(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.lorem_ipsum_medium),
    pagingItems: LazyPagingItems<MiniAnime>?,
    onNavigateDetailsClick: (id: String) -> Unit,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        Message(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.label_no_anime_result,
        )
    },
    errorContent: @Composable LazyItemScope.(errorMessage: String) -> Unit = { errorMessage ->
        Error(
            modifier = Modifier.fillMaxWidth(),
            errorMessage = errorMessage,
            onRetry = { pagingItems?.retry() },
        )
    },
) {
    AnimeRowTitle(title)

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
            pagingItems == null || pagingItems.loadState.refresh.isError -> {
                item(content = emptyContent)
            }

            pagingItems.isNotEmpty() -> {
                items(pagingItems.itemCount) { index ->
                    pagingItems[index]?.let {
                        AnimeItem(
                            anime = it,
                            onNavigateDetailsClick = onNavigateDetailsClick,
                        )
                    }
                }
            }

            pagingItems.loadState.refresh.isLoading || pagingItems.loadState.append.isLoading -> {
                items(Constants.ITEMS_PER_PAGE) {
                    PlaceholderItem()
                }
            }

            pagingItems.loadState.append.isError -> {
                item { errorContent(pagingItems.loadState.append.error.toString()) }
            }

            pagingItems.loadState.refresh.isFinished -> {
                if (pagingItems.isEmpty()) {
                    item(content = emptyContent)
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
fun AnimeRowTitle(title: String) {
    Column {
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
        isPlaceholder = true,
    )
}

@Preview
@Composable
private fun AnimeListRowPreview() {
    DoodleTheme {
        Column(
            modifier = Modifier.size(450.dp, 200.dp)
        ) {
            AnimeListRow(
                title = "Most Popular",
                pagingItems = null,
                onNavigateDetailsClick = {},
            )
        }
    }
}