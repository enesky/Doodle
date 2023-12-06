/*
 * Copyright 2022 Afig Aliyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.enesky.doodle.app.ui.component

import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.doodle.app.navigation.BottomNavBarDestinations

@Composable
fun DoodleBottomBar(
    destinations: Array<BottomNavBarDestinations>,
    currentDestination: BottomNavBarDestinations,
    onNavigateToDestination: (BottomNavBarDestinations) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = DoodleTheme.colors.darkGrey
) {
    Surface(
        modifier = modifier,
        color = color
    ) {
        val animationSpec = BottomBarAnimationSpec

        DoodleBottomNavLayout(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            ),
            selectedIndex = currentDestination.ordinal,
            itemCount = destinations.size,
            animationSpec = animationSpec,
            indicator = { DoodleBottomNavIndicator() }
        ) {
            destinations.forEach { destination ->
                val selected = destination == currentDestination
                val tint by animateColorAsState(
                    if (selected) {
                        DoodleTheme.colors.main
                    } else {
                        DoodleTheme.colors.softDark
                    }, label = ""
                )

                val icon = painterResource(id = destination.iconResourceId)
                val text = stringResource(id = destination.textResourceId)

                DoodleBottomNavigationItem(
                    modifier = Modifier
                        .padding(DoodleTheme.spacing.medium)
                        .clip(DoodleTheme.shapes.medium),
                    icon = {
                        Icon(
                            painter = icon,
                            tint = tint,
                            contentDescription = text
                        )
                    },
                    text = {
                        Text(
                            text = text,
                            color = tint,
                            style = DoodleTheme.typography.regular.h6,
                            maxLines = 1
                        )
                    },
                    selected = selected,
                    onSelect = { onNavigateToDestination(destination) },
                    animationSpec = animationSpec
                )
            }
        }
    }
}

@Composable
private fun DoodleBottomNavLayout(
    selectedIndex: Int,
    itemCount: Int,
    animationSpec: AnimationSpec<Float>,
    indicator: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val selectionFractions = remember(itemCount) {
        List(itemCount) { index ->
            Animatable(if (index == selectedIndex) 1f else 0f)
        }
    }
    selectionFractions.forEachIndexed { index, selectionFraction ->
        val targetValue = if (index == selectedIndex) 1f else 0f
        LaunchedEffect(key1 = targetValue, key2 = animationSpec) {
            selectionFraction.animateTo(targetValue = targetValue, animationSpec = animationSpec)
        }
    }

    val indicatorIndex = remember { Animatable(initialValue = 0f) }
    val targetIndicatorIndex = selectedIndex.toFloat()
    LaunchedEffect(key1 = targetIndicatorIndex) {
        indicatorIndex.animateTo(targetValue = targetIndicatorIndex, animationSpec = animationSpec)
    }

    Layout(
        modifier = modifier.height(72.dp),
        content = {
            content()
            Box(modifier = Modifier.layoutId(IndicatorLayoutId), content = indicator)
        }
    ) { measurables, constraints ->
        require(itemCount == (measurables.size - 1))

        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = 2 * unselectedWidth
        val indicatorMeasurable = measurables.first { it.layoutId == IndicatorLayoutId }

        val itemPlaceables = measurables
            .filterNot { it == indicatorMeasurable }
            .mapIndexed { index, measurable ->
                val width = lerp(
                    start = unselectedWidth,
                    stop = selectedWidth,
                    fraction = selectionFractions[index].value
                )
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width
                    )
                )
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
            constraints.copy(
                minWidth = selectedWidth,
                maxWidth = selectedWidth
            )
        )

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
        ) {
            val indicatorLeft = indicatorIndex.value * unselectedWidth
            indicatorPlaceable.placeRelative(x = indicatorLeft.toInt(), y = 0)
            var x = 0
            itemPlaceables.forEach { placeable ->
                placeable.placeRelative(x = x, y = 0)
                x += placeable.width
            }
        }
    }
}

@Composable
private fun DoodleBottomNavigationItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelect: () -> Unit,
    animationSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.selectable(selected = selected, onClick = onSelect),
        contentAlignment = Alignment.Center
    ) {
        val animationProgress by animateFloatAsState(
            targetValue = if (selected) 1f else 0f,
            animationSpec = animationSpec
        )
        DoodleBottomNavItemLayout(
            icon = icon,
            text = text,
            animationProgress = animationProgress
        )
    }
}

@Composable
private fun DoodleBottomNavItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float,
    modifier: Modifier = Modifier,
    defaultTransformOrigin: TransformOrigin = TransformOrigin(
        TransformOriginPivotFractionX,
        TransformOriginPivotFractionY
    )
) {
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .layoutId(IconLayoutId)
                    .padding(horizontal = DoodleTheme.spacing.xSmall),
                content = icon
            )
            val scale = lerp(
                start = BottomNavItemLayoutLerpStart,
                stop = BottomNavItemLayoutLerpStop,
                fraction = animationProgress
            )
            Box(
                modifier = Modifier
                    .layoutId(TextLayoutId)
                    .padding(horizontal = DoodleTheme.spacing.xSmall)
                    .graphicsLayer {
                        alpha = animationProgress
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = defaultTransformOrigin
                    },
                content = text
            )
        },
        modifier = modifier
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == IconLayoutId }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == TextLayoutId }.measure(constraints)

        placeTextAndIcon(
            textPlaceable = textPlaceable,
            iconPlaceable = iconPlaceable,
            width = constraints.maxWidth,
            height = constraints.maxHeight,
            animationProgress = animationProgress
        )
    }
}

@Composable
private fun DoodleBottomNavIndicator(
    modifier: Modifier = Modifier,
    padding: Dp = DoodleTheme.spacing.medium,
    color: Color = DoodleTheme.colors.secondary,
    shape: Shape = DoodleTheme.shapes.medium
) {
    Spacer(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = color, shape = shape)
    )
}

@PreviewUiMode
@Composable
private fun DoodleBottomBarPreview() {
    DoodleTheme {
        DoodleBottomBar(
            destinations = BottomNavBarDestinations.values(),
            currentDestination = BottomNavBarDestinations.Home,
            onNavigateToDestination = {},
            modifier = Modifier
                .fillMaxSize()
                .background(DoodleTheme.colors.darkGrey)
        )
    }
}

@PreviewUiMode
@Composable
private fun DoodleBottomNavItemLayoutPreview() {
    DoodleTheme(isDarkTheme = true) {
        DoodleBottomNavItemLayout(
            icon = { Icon(painter = painterResource(id = BottomNavBarDestinations.Home.iconResourceId), contentDescription = "") },
            text = { Text(text = stringResource(id = BottomNavBarDestinations.Home.textResourceId)) },
            animationProgress = 1f
        )
    }
}

@PreviewUiMode
@Composable
private fun DoodleBottomNavIndicatorPreview() {
    DoodleTheme {
        DoodleBottomNavIndicator()
    }
}

private fun MeasureScope.placeTextAndIcon(
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
): MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2

    val textWidth = textPlaceable.width * animationProgress
    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = iconX + iconPlaceable.width

    return layout(width = width, height = height) {
        iconPlaceable.placeRelative(x = iconX.toInt(), y = iconY)
        if (animationProgress != 0f) {
            textPlaceable.placeRelative(x = textX.toInt(), y = textY)
        }
    }
}

private const val TextLayoutId = "text"
private const val IconLayoutId = "icon"
private const val IndicatorLayoutId = "indicator"
private const val TransformOriginPivotFractionX = 0f
private const val TransformOriginPivotFractionY = 0.5f
private const val BottomNavItemLayoutLerpStart = 0.6f
private const val BottomNavItemLayoutLerpStop = 1f
private val BottomBarAnimationSpec = SpringSpec<Float>(
    stiffness = 800f,
    dampingRatio = 0.8f
)