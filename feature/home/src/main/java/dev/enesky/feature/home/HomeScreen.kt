package dev.enesky.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.design_system.annotation.PreviewUiMode
import dev.enesky.core.design_system.components.SwipeRefresh
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.domain.utils.isLoading
import dev.enesky.core.ui.components.home.AnimeListRow
import dev.enesky.core.ui.components.home.AnimePreview
import dev.enesky.feature.home.helpers.HomeEvents
import dev.enesky.feature.home.helpers.HomeUiState
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onShowMessage: (String) -> Unit,
    onNavigateDetailsClick: (id: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.errorMessage != null) {
        onShowMessage(uiState.errorMessage!!)
    }

    ObserveAsEvents(flow = viewModel.eventFlow) { homeEvents ->
        when (homeEvents) {
            is HomeEvents.OnError -> onShowMessage(homeEvents.errorMessage)
            is HomeEvents.NavigateToDetails -> onNavigateDetailsClick(homeEvents.animeId)
            is HomeEvents.OnItemOptionsClick -> {
                // TODO: Add item options click action -> Open a bottom sheet or options menu
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onNavigateDetailsClick = onNavigateDetailsClick,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState(),
    onNavigateDetailsClick: (id: String) -> Unit,
) {
    val popularPagingItems = uiState.popularAnimes?.collectAsLazyPagingItems()
    val airingPagingItems = uiState.airingAnimes?.collectAsLazyPagingItems()
    val upcomingPagingItems = uiState.upcomingAnimes?.collectAsLazyPagingItems()
    val favoritePagingItems = uiState.favoriteAnimes?.collectAsLazyPagingItems()

    fun isRefreshing() = popularPagingItems?.loadState?.refresh?.isLoading == true ||
        airingPagingItems?.loadState?.refresh?.isLoading == true ||
        upcomingPagingItems?.loadState?.refresh?.isLoading == true ||
        favoritePagingItems?.loadState?.refresh?.isLoading == true

    fun refresh() {
        airingPagingItems?.refresh()
        upcomingPagingItems?.refresh()
        popularPagingItems?.refresh()
        favoritePagingItems?.refresh()
    }

    SwipeRefresh(
        modifier = modifier,
        isRefreshing = isRefreshing(),
        onRefresh = { refresh() },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = DoodleTheme.spacing.statusBarPadding,
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(),
            ),
        ) {
            item {
                AnimePreview(
                    anime = uiState.previewAnime,
                    isLoading = uiState.loading,
                    onNavigateDetailsClick = onNavigateDetailsClick,
                )
            }
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

@PreviewUiMode
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState(),
    ) { }
}
