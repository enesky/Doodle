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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun DoodleSnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    // TODO: -> containerColor: Color = CinemaxTheme.colors.primarySoft,
    // TODO: -> contentColor: Color = CinemaxTheme.colors.whiteGrey,
    // TODO: -> actionColor: Color = CinemaxTheme.colors.primaryBlue
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
    ) { snackbarData ->
        Snackbar(
            snackbarData = snackbarData,
            shape = shape,
            // TODO: -> containerColor = containerColor,
            // TODO: -> contentColor = contentColor,
            // TODO: -> actionColor = actionColor
        )
    }
}

val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error(LocalSnackbarHostStateErrorMessage)
}

private const val LocalSnackbarHostStateErrorMessage = "No SnackbarHostState provided."
