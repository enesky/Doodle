package dev.enesky.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.enesky.core.common.utils.Logger
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.design_system.common.CenteredBox
import dev.enesky.core.design_system.common.DoodleImagePlaceholder
import dev.enesky.core.design_system.common.DoodleNetworkImage
import dev.enesky.core.design_system.common.Message
import dev.enesky.core.design_system.common.SwipeRefresh
import dev.enesky.core.design_system.common.placeholder
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.design_system.theme.Icons
import dev.enesky.core.network.model.ImageList
import dev.enesky.core.network.model.Images
import dev.enesky.core.network.model.MiniAnime
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.util.error
import dev.enesky.core.ui.util.isEmpty
import dev.enesky.core.ui.util.isError
import dev.enesky.core.ui.util.isFinished
import dev.enesky.core.ui.util.isLoading
import dev.enesky.core.ui.util.isNotEmpty
import dev.enesky.feature.home.helpers.HomeEvents
import kotlinx.coroutines.flow.emptyFlow
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateDetailsClick: (id: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val airingAnimes = (uiState.airingAnimes ?: emptyFlow()).collectAsLazyPagingItems()
    val upcomingAnimes = (uiState.upcomingAnimes ?: emptyFlow()).collectAsLazyPagingItems()
    val popularAnimes = (uiState.popularAnimes ?: emptyFlow()).collectAsLazyPagingItems()
    val favoriteAnimes = (uiState.favoriteAnimes ?: emptyFlow()).collectAsLazyPagingItems()

    ObserveAsEvents(flow = viewModel.eventFlow) { homeEvents ->
        when (homeEvents) {
            is HomeEvents.OnError -> {
                Logger.debug("HomeScreen", "onError: ${homeEvents.errorMessage}")
            }

            is HomeEvents.NavigateToDetails -> {
                onNavigateDetailsClick(homeEvents.animeId)
            }

            is HomeEvents.OnItemOptionsClick -> {
                // TODO: Add item options click action -> Open a bottom sheet or options menu
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        airingPagingItems = airingAnimes,
        upcomingPagingItems = upcomingAnimes,
        popularPagingItems = popularAnimes,
        favoritePagingItems = favoriteAnimes,
        onNavigateDetailsClick = onNavigateDetailsClick,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    airingPagingItems: LazyPagingItems<MiniAnime> = emptyFlow<PagingData<MiniAnime>>().collectAsLazyPagingItems(),
    upcomingPagingItems: LazyPagingItems<MiniAnime> = emptyFlow<PagingData<MiniAnime>>().collectAsLazyPagingItems(),
    popularPagingItems: LazyPagingItems<MiniAnime> = emptyFlow<PagingData<MiniAnime>>().collectAsLazyPagingItems(),
    favoritePagingItems: LazyPagingItems<MiniAnime> = emptyFlow<PagingData<MiniAnime>>().collectAsLazyPagingItems(),
    onNavigateDetailsClick: (id: String) -> Unit,
) {
    SwipeRefresh(
        modifier = modifier,
        isRefreshing = popularPagingItems.loadState.refresh.isLoading,
        onRefresh = popularPagingItems::refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                vertical = DoodleTheme.spacing.medium,
            ),
        ) {
            // TODO: Preview most popular anime
            item {
                AnimeListRow(
                    title = "Most Popular",
                    pagingItems = popularPagingItems,
                    onNavigateDetailsClick = onNavigateDetailsClick,
                )
            }
            item {
                AnimeListRow(
                    title = "Airing",
                    pagingItems = airingPagingItems,
                    onNavigateDetailsClick = onNavigateDetailsClick,
                )
            }
            item {
                AnimeListRow(
                    title = "Upcoming",
                    pagingItems = upcomingPagingItems,
                    onNavigateDetailsClick = onNavigateDetailsClick,
                )
            }
            item {
                AnimeListRow(
                    title = "Favorites",
                    pagingItems = favoritePagingItems,
                    onNavigateDetailsClick = onNavigateDetailsClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AnimeListRow(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = dev.enesky.core.design_system.R.string.lorem_ipsum_medium),
    pagingItems: LazyPagingItems<MiniAnime>,
    onNavigateDetailsClick: (id: String) -> Unit,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        Message(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = dev.enesky.core.design_system.R.string.label_no_anime_result,
        )
    },
    loadingContent: @Composable LazyItemScope.() -> Unit = {
        // CircularProgressIndicator()
    },
    errorContent: @Composable LazyItemScope.(errorMessage: String) -> Unit = { errorMessage ->
        //Error(
        //    modifier = Modifier.fillMaxWidth(),
        //    errorMessage = errorMessage,
        //    onRetry = tvShows::retry
        //)
    }
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
    Spacer(modifier = Modifier.size(DoodleTheme.spacing.xSmall))

    val rowState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DoodleTheme.spacing.medium),
        contentPadding = PaddingValues(
            horizontal = DoodleTheme.spacing.medium,
        ),
        state = rowState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = rowState)
    ) {
        when {
            pagingItems.isEmpty() -> {
                item {
                    Text(
                        text = "Loading...",
                        style = DoodleTheme.typography.regular.h1,
                    )
                }
            }
            pagingItems.isNotEmpty() -> {
                items(pagingItems.itemCount) { index ->
                    val anime = pagingItems[index]
                    if (anime != null) {
                        AnimeItem(
                            anime = anime,
                            onNavigateDetailsClick = onNavigateDetailsClick,
                        )
                    } else {
                        Text(
                            text = "Anime not found...",
                            style = DoodleTheme.typography.regular.h1,
                        )
                    }
                }
            }
            pagingItems.loadState.refresh.isLoading -> {
                items(10) { AnimeItem(
                    anime = MiniAnime(
                        id = 0,
                        title = "",
                        genres = emptyList(),
                        trailer = null,
                        url = "",
                        images = Images(
                            jpg = ImageList(
                                imageUrl = "",
                            ),
                        ),
                    ),
                    isPlaceholder = true
                ) }
            }
            pagingItems.loadState.refresh.isFinished -> {
                if (pagingItems.isEmpty()) {
                    item(content = emptyContent)
                }
            }
            pagingItems.loadState.refresh.isError -> {
                item(content = emptyContent)
            }
        }
        if (pagingItems.loadState.append.isLoading) {
            item { CenteredBox(modifier = Modifier.fillMaxWidth()) { loadingContent() } }
        }
        if (pagingItems.loadState.append.isError) {
            item { errorContent(errorMessage = pagingItems.loadState.append.error.toString()) }
        }
    }
    Spacer(modifier = Modifier.size(DoodleTheme.spacing.xSmall))
}

@Composable
private fun AnimeItem(
    anime: MiniAnime,
    isPlaceholder: Boolean = false,
    onNavigateDetailsClick: ((id: String) -> Unit)? = null,
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val itemWidth = screenWidth / 3.9f
    val itemHeight = itemWidth * 1.5f

    val genreList = anime.genres.map { it.name }.take(2).toString()
    val genres = genreList
        .replace("[", "")
        .replace("]", "")
        .replace(", ", " | ")

    Column(
        modifier = if (isPlaceholder) Modifier else Modifier.clickable {
            onNavigateDetailsClick?.invoke(anime.id.toString())
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
                contentDescription = anime.title
            )
        }
        Row(
            modifier = Modifier
                .width(itemWidth),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    modifier = if (isPlaceholder) {
                        Modifier
                            .fillMaxWidth()
                            .placeholder(color = DoodleTheme.colors.softDark)
                    } else {
                        Modifier
                    },
                    text = anime.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = DoodleTheme.typography.regular.h6,
                )
                Text(
                    modifier = if (isPlaceholder) {
                        Modifier
                            .fillMaxWidth()
                            .placeholder(color = DoodleTheme.colors.softDark)
                    } else {
                        Modifier
                    },
                    text = genres,
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
                            start = DoodleTheme.spacing.xxxSmall,
                            end = DoodleTheme.spacing.default,
                            top = DoodleTheme.spacing.xxSmall,
                            bottom = DoodleTheme.spacing.xxSmall,
                        ),
                    imageVector = Icons.MoreVert,
                    tint = DoodleTheme.colors.white,
                    contentDescription = anime.title + "options",
                )
            }
        }
    }
}

@PreviewUiMode
@Composable
private fun HomeScreenPreview() {
    HomeScreen { }
}
