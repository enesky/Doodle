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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DefaultSpace = 0.dp
private val BorderStroke = 1.dp
private val XXXS = 2.dp
private val XXS = 4.dp
private val XS = 8.dp
private val S = 12.dp
private val M = 16.dp
private val L = 24.dp
private val XL = 32.dp
private val XXL = 40.dp
private val XXXL = 64.dp

@Immutable
data class DoodleSpacing(
    val default: Dp = DefaultSpace,
    val border: Dp = BorderStroke,
    val statusBarPadding: Dp = XXL,

    val xxxSmall: Dp = XXXS,
    val xxSmall: Dp = XXS,
    val xSmall: Dp = XS,
    val small: Dp = S,
    val medium: Dp = M,
    val large: Dp = L,
    val xLarge: Dp = XL,
    val xxLarge: Dp = XXL,
    val xxxLarge: Dp = XXXL,
)

internal val LocalDoodleSpacing = staticCompositionLocalOf { DoodleSpacing() }
