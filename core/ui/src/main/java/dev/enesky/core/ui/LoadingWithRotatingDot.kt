package dev.enesky.core.ui

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import dev.enesky.core.design_system.DoodleTheme
import kotlin.math.cos
import kotlin.math.sin

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewRotateDotAnimation() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = DoodleTheme.colors.background
    ) {
        RotateDotAnimation()
    }
}

@Composable
fun RotateDotAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
        ), label = ""
    )
    val color = DoodleTheme.colors.main

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.3f))
            .clickable(enabled = false, onClick = {}),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.background(Color.Transparent)) {
            drawCircle(
                color, center = center,
                radius = 150f,
                style = Stroke(width = 10f)
            )

            val x = (center.x + cos(Math.toRadians(rotation.toDouble())) * 120f).toFloat()
            val y = (center.y + sin(Math.toRadians(rotation.toDouble())) * 120f).toFloat()

            drawCircle(
                color,
                center = Offset(x, y),
                radius = 20f
            )
        }
    }
}
