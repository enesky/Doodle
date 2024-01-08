package dev.enesky.feature.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.enesky.core.design_system.annotation.PreviewUiMode
import dev.enesky.core.design_system.theme.DoodleTheme

/**
 * Created by Enes Kamil YILMAZ on 06/12/2023
 */

@Composable
fun ExploreRoute(
    modifier: Modifier = Modifier,
    onShowMessage: (String) -> Unit,
) {
    ExploreScreen(modifier)
}

@Composable
private fun ExploreScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = DoodleTheme.colors.background,
    ) {
        ExploreContent(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ExploreContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Welcome to Explore Screen",
            style = DoodleTheme.typography.regular.h3,
        )
    }
}

@PreviewUiMode
@Composable
private fun ExploreScreenPreview() {
    DoodleTheme {
        ExploreScreen()
    }
}
