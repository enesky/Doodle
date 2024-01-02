package dev.enesky.core.design_system.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * With systemBarsPadding(), this box will be placed between the system bars
 * Even when configuration changes happen
 */
@Composable
fun CenteredBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.systemBarsPadding(),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        content = content,
    )
}
