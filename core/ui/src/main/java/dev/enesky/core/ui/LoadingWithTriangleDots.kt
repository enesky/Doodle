package dev.enesky.core.ui

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.enesky.core.design_system.DoodleTheme

private const val DefaultAnimationDuration = 750
private const val DefaultStartDelay = 0

private val DefaultWidth = 150.dp
private val DefaultHeight = 120.dp
private val DefaultBallDiameter = 24.dp

@Preview
@Composable
fun BallTrianglePathProgressIndicatorPreview() {
    DoodleTheme {
        LoadingWithTriangleDots()
    }
}

@Composable
fun LoadingWithTriangleDots() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.3f))
            .clickable(enabled = false, onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        BallTrianglePathProgressIndicator()
    }
}

@Composable
fun BallTrianglePathProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = DoodleTheme.colors.main,
    animationDuration: Int = DefaultAnimationDuration,
    startDelay: Int = DefaultStartDelay,
    ballDiameter: Dp = DefaultBallDiameter,
    width: Dp = DefaultWidth,
    height: Dp = DefaultHeight,
) {
    val transition = rememberInfiniteTransition(label = "")

    val fraction by transition.fraction(
        durationMillis = animationDuration,
        delayMillis = startDelay,
        easing = FastOutSlowInEasing
    )

    val offset = with(LocalDensity.current) {
        val radius = ballDiameter.toPx() / 2
        arrayListOf(
            Offset((width / 2).toPx(), radius),
            Offset(width.toPx() - radius, height.toPx() - radius),
            Offset(radius, height.toPx() - radius),
        )
    }

    ProgressIndicator(modifier, width, height) {
        val offsetList = List(offset.size) { index ->
            lerp(offset[index], offset[(index + 1) % 3], fraction)
        }
        drawIndeterminateBallTrianglePathIndicator(
            diameter = ballDiameter.toPx(),
            offset = offsetList,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateBallTrianglePathIndicator(
    diameter: Float,
    offset: List<Offset>,
    color: Color
) {
    for (i in offset.indices) {
        drawCircle(
            color = color,
            radius = diameter / 2,
            center = offset[i],
        )
    }
}

@Composable
internal fun ProgressIndicator(
    modifier: Modifier,
    width: Dp,
    height: Dp,
    onDraw: DrawScope.() -> Unit
) {
    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(width, height)
            .focusable(),
        onDraw = onDraw
    )
}

@Composable
internal fun InfiniteTransition.fraction(
    durationMillis: Int,
    delayMillis: Int = 0,
    easing: Easing = LinearEasing
): State<Float> {
    val duration = durationMillis + delayMillis

    return animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                this.durationMillis = duration
                0f at delayMillis / 2 with easing
                1f at durationMillis + (delayMillis / 2)
                1f at duration
            }
        ),
        label = ""
    )
}
