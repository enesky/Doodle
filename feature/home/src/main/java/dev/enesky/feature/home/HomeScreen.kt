package dev.enesky.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.enesky.core.common.utils.Logger
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.design_system.common.SwipeRefresh
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.network.model.Anime
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.util.isEmpty
import dev.enesky.core.ui.util.isLoading
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
    val popularAnimes = (uiState.popularAnimes ?: emptyFlow()).collectAsLazyPagingItems()

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
        popularAnimes = popularAnimes,
        onNavigateDetailsClick = onNavigateDetailsClick,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    popularAnimes: LazyPagingItems<Anime>,
    onNavigateDetailsClick: (id: String) -> Unit,
) {
    SwipeRefresh(
        modifier = modifier,
        isRefreshing = popularAnimes.loadState.refresh.isLoading,
        onRefresh = popularAnimes::refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(DoodleTheme.spacing.medium),
            contentPadding = PaddingValues(DoodleTheme.spacing.large),
        ) {
            when {
                popularAnimes.isEmpty() -> {
                    item {
                        Text(
                            text = "Loading...",
                            style = DoodleTheme.typography.regular.h1,
                        )
                    }
                }

                else -> {
                    item {
                        Text(
                            text = "Popular Animes",
                            style = DoodleTheme.typography.regular.h1,
                        )
                    }

                    items(popularAnimes.itemCount) { index ->
                        val anime = popularAnimes[index]

                        if (anime != null) {
                            AnimeItem(
                                anime = anime,
                                onNavigateDetailsClick = onNavigateDetailsClick,
                            )
                        } else {
                            Text(
                                text = "Anime item is null :(",
                                style = DoodleTheme.typography.regular.h1,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeItem(
    anime: Anime,
    onNavigateDetailsClick: (id: String) -> Unit,
) {
    // TODO: Add placeholder list
    Column(
        modifier = Modifier
            .padding(DoodleTheme.spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = anime.title,
            style = DoodleTheme.typography.regular.h1,
        )
    }
}

@PreviewUiMode
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        popularAnimes = emptyFlow<PagingData<Anime>>().collectAsLazyPagingItems()
    ) { }
}
