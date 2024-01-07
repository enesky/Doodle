/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
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
package dev.enesky.core.design_system.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

// TODO: Update here with Theme Selector
@Composable
fun DoodleTheme(
    isDarkTheme: Boolean = true,
    shapes: Shapes = Shapes,
    typography: Typography = Typography,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme,
        shapes = shapes,
        typography = typography,
    ) {
        ProvideDoodleThemeDependencies(
            isDarkTheme = isDarkTheme,
            content = content,
        )
    }
}

@Composable
private fun ProvideDoodleThemeDependencies(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDoodleColors provides DoodleColors(isSystemInDarkTheme = isDarkTheme),
        LocalDoodleShapes provides DoodleShapes(),
        LocalDoodleTypography provides DoodleTypography(),
        LocalDoodleSpacing provides DoodleSpacing(),
        LocalIndication provides rememberDoodleRipple(),
        LocalRippleTheme provides DoodleRippleTheme,
    ) {
        ProvideTextStyle(
            value = DoodleTheme.typography.regular.h5,
            content = content,
        )
    }
}

object DoodleTheme {
    val colors: DoodleColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDoodleColors.current

    val shapes: DoodleShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalDoodleShapes.current

    val typography: DoodleTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDoodleTypography.current

    val spacing: DoodleSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalDoodleSpacing.current
}
