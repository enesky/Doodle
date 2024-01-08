package dev.enesky.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.enesky.core.common.utils.Logger
import dev.enesky.core.design_system.annotation.PreviewUiMode
import dev.enesky.core.design_system.components.CenteredBox
import dev.enesky.core.design_system.theme.DoodleTheme

/**
 * Created by Enes Kamil YILMAZ on 05/12/2023
 */

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    onShowMessage: (String) -> Unit = {},
) {
    SettingsScreen(
        modifier = modifier,
        onShowMessage = onShowMessage,
    )
}

@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    onShowMessage: (String) -> Unit,
) {
    CenteredBox(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Welcome to Settings Screen",
                style = DoodleTheme.typography.regular.h3,
            )
            Button(
                modifier = Modifier.padding(32.dp),
                onClick = {
                    onShowMessage("Test message")
                },
            ) {
                Text(text = "Click me for nothing :)", color = Color.White)
            }
        }
    }
}

@PreviewUiMode
@Composable
private fun SettingsScreenPreview() {
    DoodleTheme {
        SettingsScreen {}
    }
}
