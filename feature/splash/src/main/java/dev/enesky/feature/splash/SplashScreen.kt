package dev.enesky.feature.splash

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
import dev.enesky.core.design_system.components.CenteredBox
import dev.enesky.core.design_system.theme.DoodleTheme

/**
 * Created by Enes Kamil YILMAZ on 04/01/2024
 */

@Composable
fun SplashRoute(
    modifier: Modifier = Modifier,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
) {



    SplashScreen(
        modifier = modifier,
    )
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = DoodleTheme.colors.background,
    ) {
        SplashContent(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SplashContent(
    modifier: Modifier = Modifier,
) {
    CenteredBox(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Welcome to Splash Screen",
                style = DoodleTheme.typography.regular.h3,
            )
        }
    }
}

@PreviewUiMode
@Composable
private fun SplashScreenPreview() {
    DoodleTheme {
        SplashScreen()
    }
}
