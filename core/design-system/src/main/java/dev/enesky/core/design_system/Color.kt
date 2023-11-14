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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val light_primary = Color(0xFFA14000)
val light_onPrimary = Color(0xFFFFFFFF)
val light_primaryContainer = Color(0xFFFFDBCC)
val light_onPrimaryContainer = Color(0xFF351000)
val light_secondary = Color(0xFF974800)
val light_onSecondary = Color(0xFFFFFFFF)
val light_secondaryContainer = Color(0xFFFFDBC7)
val light_onSecondaryContainer = Color(0xFF311300)
val light_tertiary = Color(0xFF006689)
val light_onTertiary = Color(0xFFFFFFFF)
val light_tertiaryContainer = Color(0xFFC2E8FF)
val light_onTertiaryContainer = Color(0xFF001E2C)
val light_error = Color(0xFFBA1A1A)
val light_errorContainer = Color(0xFFFFDAD6)
val light_onError = Color(0xFFFFFFFF)
val light_onErrorContainer = Color(0xFF410002)
val light_background = Color(0xFFF8FDFF)
val light_onBackground = Color(0xFF001F25)
val light_surface = Color(0xFFF8FDFF)
val light_onSurface = Color(0xFF001F25)
val light_surfaceVariant = Color(0xFFF4DED5)
val light_onSurfaceVariant = Color(0xFF52443D)
val light_outline = Color(0xFF85736C)
val light_inverseOnSurface = Color(0xFFD6F6FF)
val light_inverseSurface = Color(0xFF00363F)
val light_inversePrimary = Color(0xFFFFB694)
val light_shadow = Color(0xFF000000)
val light_surfaceTint = Color(0xFFA14000)
val light_outlineVariant = Color(0xFFD8C2BA)
val light_scrim = Color(0xFF000000)

val dark_primary = Color(0xFFFFB694)
val dark_onPrimary = Color(0xFF571F00)
val dark_primaryContainer = Color(0xFF7B2F00)
val dark_onPrimaryContainer = Color(0xFFFFDBCC)
val dark_secondary = Color(0xFFFFB688)
val dark_onSecondary = Color(0xFF512400)
val dark_secondaryContainer = Color(0xFF733500)
val dark_onSecondaryContainer = Color(0xFFFFDBC7)
val dark_tertiary = Color(0xFF78D1FF)
val dark_onTertiary = Color(0xFF003549)
val dark_tertiaryContainer = Color(0xFF004D68)
val dark_onTertiaryContainer = Color(0xFFC2E8FF)
val dark_error = Color(0xFFFFB4AB)
val dark_errorContainer = Color(0xFF93000A)
val dark_onError = Color(0xFF690005)
val dark_onErrorContainer = Color(0xFFFFDAD6)
val dark_background = Color(0xFF001F25)
val dark_onBackground = Color(0xFFA6EEFF)
val dark_surface = Color(0xFF001F25)
val dark_onSurface = Color(0xFFA6EEFF)
val dark_surfaceVariant = Color(0xFF52443D)
val dark_onSurfaceVariant = Color(0xFFD8C2BA)
val dark_outline = Color(0xFFA08D85)
val dark_inverseOnSurface = Color(0xFF001F25)
val dark_inverseSurface = Color(0xFFA6EEFF)
val dark_inversePrimary = Color(0xFFA14000)
val dark_shadow = Color(0xFF000000)
val dark_surfaceTint = Color(0xFFFFB694)
val dark_outlineVariant = Color(0xFF52443D)
val dark_scrim = Color(0xFF000000)


val seed = Color(0xFFF2712B)
val CustomColor1 = Color(0xFF813724)
val light_CustomColor1 = Color(0xFF9B432D)
val light_onCustomColor1 = Color(0xFFFFFFFF)
val light_CustomColor1Container = Color(0xFFFFDBD2)
val light_onCustomColor1Container = Color(0xFF3C0800)
val dark_CustomColor1 = Color(0xFFFFB4A2)
val dark_onCustomColor1 = Color(0xFF5E1704)
val dark_CustomColor1Container = Color(0xFF7D2D18)
val dark_onCustomColor1Container = Color(0xFFFFDBD2)

// Selected Colors
val blazeOrange = Color(0xFFF96D00)
val crusta = Color(0xFFFD8731)
val ebonyClay = Color(0xFF2B323F)
val limedSpruce = Color(0xFF334048)
val soothingBreeze = Color(0xFFB2BEC3)
val geyser = Color(0xFFDFE6E9)
val eastBay = Color(0xFF4D4C7D)
val rhino = Color(0xFF363062)

val selectedColorsList = listOf(
    "blazeOrange" to blazeOrange,
    "crusta" to crusta,
    "ebonyClay" to ebonyClay,
    "limedSpruce" to limedSpruce,
    "soothingBreeze" to soothingBreeze,
    "geyser" to geyser,
    "eastBay" to eastBay,
    "rhino" to rhino,
)

/**
 * To see the preview of the colors
 */
@Composable
private fun ColorPicker(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Selected Colors",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp),
                textDecoration = TextDecoration.Underline
            )
            LazyColumn {
                items(selectedColorsList.size) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp),
                        color = selectedColorsList[it].second,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = selectedColorsList[it].first,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f),
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = selectedColorsList[it].first,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f),
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = selectedColorsList[it].first,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f),
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorPicketPreview() {
    ColorPicker()
}
