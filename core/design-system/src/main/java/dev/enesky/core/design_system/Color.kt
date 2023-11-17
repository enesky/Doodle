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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object LightThemeColors {
    val blazeOrange = Color(0xFFF96D00) // Main color
    val ebonyClay = Color(0xFF2B323F) // Background color on dark mode
    val white = Color.White // Text color on dark background
    val soothingBreeze = Color(0xFFB2BEC3) // Second text or second background color on dark mode
}

object DarkThemeColors {
    val blazeOrange = Color(0xFFF96D00) // Main color
    val geyser = Color(0xFFDFE6E9) // Background color on light mode
    val black = Color.Black // Text color on light background
    val soothingBreeze = Color(0xFFB2BEC3) // Second text or second background color on dark mode
}

private val lightColors = ImmutableList(
    list = listOf(
        "main color" to LightThemeColors.blazeOrange,
        "background color" to LightThemeColors.ebonyClay,
        "text color" to LightThemeColors.white,
        "secondary background color" to LightThemeColors.soothingBreeze,
    ),
)

private val darkColors = ImmutableList(
    list = listOf(
        "main color" to DarkThemeColors.blazeOrange,
        "background color" to DarkThemeColors.geyser,
        "text color" to DarkThemeColors.black,
        "secondary background color" to DarkThemeColors.soothingBreeze,
    ),
)

@Immutable
data class ImmutableList<T>(
    val list: List<T>,
)

/**
 * To see the preview of the colors
 */
@Suppress("LongMethod")
@Composable
private fun ColorPicker(
    modifier: Modifier = Modifier,
    colorList: ImmutableList<Pair<String, Color>> = darkColors,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn {
            items(colorList.list.size) {
                Surface(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(1.dp),
                    color = colorList.list[it].second,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = it.toString() + " - " + colorList.list[it].first,
                            color = colorList.list[2].second,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LightThemeColorsPreview() {
    ColorPicker(colorList = lightColors)
}

@Preview
@Composable
private fun DarkThemeColorsPreview() {
    ColorPicker(colorList = darkColors)
}
