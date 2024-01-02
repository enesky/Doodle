package dev.enesky.core.design_system.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Set status bar and navigation bar color to transparent
 **/
@Composable
fun TransparentSystemBars() {
    TransparentStatusBar()
    TransparentNavigationBar()
}

/**
 * Set status bar color to transparent
 **/
@Composable
fun TransparentStatusBar() {
    val systemUiController = rememberSystemUiController()
    // TODO: Add theme support
    val useDarkIcons = false

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
        )
        onDispose {}
    }
}

/**
 * Set navigation bar color to transparent
 **/
@Composable
fun TransparentNavigationBar() {
    val systemUiController = rememberSystemUiController()
    // TODO: Add theme support
    val useDarkIcons = false

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
        )
        onDispose {}
    }
}
