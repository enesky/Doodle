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
package dev.enesky.core.design_system

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val BlazeOrange = Color(0xFFF96D00)     // Main color
private val EbonyClay = Color(0xFF2B323F)       // Background color on dark mode
private val White = Color(0xFFFFFFFF)           // Text color on dark background
private val Black = Color(0xFF171725)           // Text color on light background
private val Geyser = Color(0xFFDFE6E9)          // Background color on light mode
private val SoothingBreeze = Color(0xFFB2BEC3)  // Second text or second background color
private val Dark = Color(0xFF1F1D2B)
private val Soft = Color(0xFF252836)
private val BlueAccent = Color(0xFF12CDD9)
private val Green = Color(0xFF22B07D)
private val Orange = Color(0xFFFF8700)
private val Red = Color(0xFFFB4141)
private val Grey = Color(0xFF92929D)
private val DarkGrey = Color(0xFF696974)
private val WhiteGrey = Color(0xFFEBEBEF)
private val LineDark = Color(0xFFEAEAEA)

internal val DarkColorScheme = darkColorScheme(
    primary = BlazeOrange,
    secondary = White,
    background = EbonyClay,
    surface = SoothingBreeze
)

internal val LightColorScheme = darkColorScheme(
    primary = BlazeOrange,
    secondary = Black,
    background = Geyser,
    surface = SoothingBreeze
)

@Immutable
data class DoodleColors(
    val isSystemInDarkTheme: Boolean = false,
    val default: Color = Color.Unspecified,
    val transparent: Color = Color.Transparent,
    val main: Color = BlazeOrange,
    val background: Color = if (isSystemInDarkTheme) EbonyClay else SoothingBreeze,
    val backgroundDark: Color = EbonyClay,
    val backgroundLight: Color = Geyser,
    val text: Color = if (isSystemInDarkTheme) White else Black,
    val textDark: Color = White,
    val textLight: Color = Black,
    val secondary: Color = SoothingBreeze,
    val dark: Color = Dark,
    val softDark: Color = Soft,
    val blue: Color = BlueAccent,
    val green: Color = Green,
    val lightOrange: Color = Orange,
    val red: Color = Red,
    val white: Color = White,
    val whiteGrey: Color = WhiteGrey,
    val black: Color = Black,
    val grey: Color = Grey,
    val darkGrey: Color = DarkGrey,
    val lineDark: Color = LineDark
)

internal val LocalDoodleColors = staticCompositionLocalOf { DoodleColors() }
