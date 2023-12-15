package dev.enesky.core.design_system.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.annotation.PreviewUiMode

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = DoodleTheme.colors.background,
    contentColor: Color = DoodleTheme.colors.main,
    indicatorPadding: PaddingValues = PaddingValues(0.dp),
    indicatorAlignment: Alignment = Alignment.TopCenter,
    scale: Boolean = true,
    content: @Composable () -> Unit
) {
    val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh)
    Box(modifier = modifier.pullRefresh(state = state)) {
        content()

        PullRefreshIndicator(
            modifier = Modifier
                .padding(indicatorPadding)
                .align(indicatorAlignment),
            refreshing = isRefreshing,
            state = state,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            scale = scale
        )
    }
}

@PreviewUiMode
@Composable
private fun SwipeRefreshPreview() {
    SwipeRefresh(
        isRefreshing = true,
        onRefresh = {},
        modifier = Modifier,
        backgroundColor = DoodleTheme.colors.background,
        contentColor = DoodleTheme.colors.main,
        indicatorPadding = PaddingValues(0.dp),
        indicatorAlignment = Alignment.TopCenter,
        scale = true,
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(text = "SwipeRefresh")
        }
    }
}
