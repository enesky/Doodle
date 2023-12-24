package dev.enesky.core.design_system.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import dev.enesky.core.design_system.R
import dev.enesky.core.design_system.theme.DoodleTheme

@Composable
fun Error(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DoodleTheme.shapes.medium,
    containerColor: Color = DoodleTheme.colors.softDark,
    errorTextColor: Color = DoodleTheme.colors.white,
    actionButtonColor: Color = DoodleTheme.colors.secondary,
    errorTextStyle: TextStyle = DoodleTheme.typography.regular.h4,
    @StringRes actionButtonTextResourceId: Int = R.string.lorem_ipsum_short,
    shouldShowOfflineMode: Boolean = false,
    onOfflineModeClick: () -> Unit = {},
    offlineModeButtonColor: Color = DoodleTheme.colors.main,
    @StringRes offlineModeButtonTextResourceId: Int = R.string.lorem_ipsum_short,
) {
    Column(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(DoodleTheme.spacing.small),
    ) {
        Card(
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = containerColor),
        ) {
            Column(
                modifier = Modifier.padding(DoodleTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(DoodleTheme.spacing.small),
            ) {
                Text(
                    text = errorMessage,
                    style = errorTextStyle,
                    color = errorTextColor,
                )
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    textResourceId = actionButtonTextResourceId,
                    onClick = onRetry,
                    containerColor = DoodleTheme.colors.softDark,
                    contentColor = actionButtonColor,
                )
            }
        }
        if (shouldShowOfflineMode) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                textResourceId = offlineModeButtonTextResourceId,
                onClick = onOfflineModeClick,
                containerColor = DoodleTheme.colors.dark,
                contentColor = offlineModeButtonColor,
            )
        }
    }
}

@Composable
fun CenteredError(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DoodleTheme.shapes.medium,
    containerColor: Color = DoodleTheme.colors.softDark,
    errorTextColor: Color = DoodleTheme.colors.secondary,
    actionButtonColor: Color = DoodleTheme.colors.main,
    @StringRes actionButtonTextResourceId: Int = R.string.lorem_ipsum_short,
    shouldShowOfflineMode: Boolean = false,
    onOfflineModeClick: () -> Unit = {},
    offlineModeButtonColor: Color = DoodleTheme.colors.green,
    @StringRes offlineModeButtonTextResourceId: Int = R.string.lorem_ipsum_short,
) {
    CenteredBox(
        modifier = modifier
            .padding(horizontal = DoodleTheme.spacing.medium)
            .fillMaxSize(),
    ) {
        Error(
            errorMessage = errorMessage,
            onRetry = onRetry,
            shape = shape,
            containerColor = containerColor,
            errorTextColor = errorTextColor,
            actionButtonColor = actionButtonColor,
            actionButtonTextResourceId = actionButtonTextResourceId,
            shouldShowOfflineMode = shouldShowOfflineMode,
            onOfflineModeClick = onOfflineModeClick,
            offlineModeButtonColor = offlineModeButtonColor,
            offlineModeButtonTextResourceId = offlineModeButtonTextResourceId,
        )
    }
}
