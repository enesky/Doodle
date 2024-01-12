package dev.enesky.core.ui.components.details

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.components.home.TitleRow

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

@Suppress("LongMethod")
@Composable
fun DetailedAnimeSummary(
    modifier: Modifier = Modifier,
    summary: String,
    isLoading: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }
    var isTextLong by remember { mutableStateOf(false) }
    val maxLine = 5
    val maxLines = if (expanded) Int.MAX_VALUE else maxLine

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TitleRow(Modifier, "Summary")

        Box {
            BasicText(
                modifier = Modifier
                    .padding(
                        start = DoodleTheme.spacing.medium,
                        end = DoodleTheme.spacing.medium,
                        top = DoodleTheme.spacing.medium,
                        bottom = DoodleTheme.spacing.large,
                    )
                    .animateContentSize(),
                text = AnnotatedString(summary),
                color = { Color.White },
                style = DoodleTheme.typography.regular.h6,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { it: TextLayoutResult ->
                    // Don't show "Show more" button when the text is now long enough
                    isTextLong = it.didOverflowHeight || it.lineCount > maxLine
                },
            )

            // Foreground gradient
            Box(
                modifier = if (isTextLong) {
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    DoodleTheme.colors.transparent,
                                    DoodleTheme.colors.transparent,
                                    DoodleTheme.colors.transparent,
                                    DoodleTheme.colors.background.copy(alpha = 0.2f),
                                    DoodleTheme.colors.background,
                                ),
                            ),
                        )
                        .clickable {
                            expanded = !expanded
                        }
                } else {
                    Modifier.matchParentSize()
                },
                contentAlignment = Alignment.BottomCenter,
            ) {
                if (isTextLong || isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier,
                            text = if (expanded) "Show less" else "Show more",
                            color = DoodleTheme.colors.white,
                            style = DoodleTheme.typography.bold.h6.copy(
                                textAlign = TextAlign.Center,
                            ),
                        )
                        // Rotate the arrow icon by 180 degrees when expanded
                        val rotationAngle by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f,
                            animationSpec = tween(
                                durationMillis = 500,
                            ),
                            label = "",
                        )
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer {
                                    rotationZ = rotationAngle
                                },
                            painter = rememberVectorPainter(image = Icons.Default.KeyboardArrowDown),
                            tint = DoodleTheme.colors.white,
                            contentDescription = "",
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailedAnimeSummaryPreview() {
    DoodleTheme {
        DetailedAnimeSummary(
            modifier = Modifier,
            summary = stringResource(id = dev.enesky.core.design_system.R.string.lorem_ipsum_full),
        )
    }
}
