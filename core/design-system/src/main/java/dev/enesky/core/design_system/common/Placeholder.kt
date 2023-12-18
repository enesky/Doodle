package dev.enesky.core.design_system.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.google.accompanist.placeholder.material.placeholder
import dev.enesky.core.design_system.theme.DoodleTheme

@Composable
fun DoodleImagePlaceholder(
    modifier: Modifier = Modifier,
    color: Color = DoodleTheme.colors.softDark,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .placeholder(color = color),
    )
}

fun Modifier.placeholder() = composed {
    placeholder(color = DoodleTheme.colors.softDark)
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.placeholder(color: Color) = composed {
    placeholder(
        visible = true,
        color = color,
        shape = DoodleTheme.shapes.medium,
    )
}
