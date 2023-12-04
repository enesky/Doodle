package dev.enesky.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.annotation.PreviewUiMode
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun HomeRoute(
    onNavigateDetailsClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    HomeScreen(modifier, onNavigateDetailsClick)
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateDetailsClick: (id: String) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        HomeContent(
            modifier = Modifier.fillMaxWidth(),
            onNavigateDetailsClick = onNavigateDetailsClick,
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onNavigateDetailsClick: (id: String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Welcome to Home Screen",
            style = MaterialTheme.typography.headlineLarge,
        )
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                onNavigateDetailsClick("1")
            },
        ) {
            Text(text = "Navigate to Details", color = Color.White)
        }
    }
}

@PreviewUiMode
@Composable
private fun HomeScreenPreview() {
    DoodleTheme {
        HomeScreen(
            onNavigateDetailsClick = {},
        )
    }
}
