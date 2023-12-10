package dev.enesky.feature.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.annotation.PreviewUiMode

/**
 * Created by Enes Kamil YILMAZ on 06/12/2023
 */

@Composable
fun ExploreRoute(
    modifier: Modifier = Modifier,
) {
    ExploreScreen(modifier)
}

@Composable
private fun ExploreScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
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
            style = MaterialTheme.typography.headlineLarge,
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