package dev.enesky.doodle.app.ui

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.enesky.core.design_system.components.TransparentSystemBars
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.doodle.app.navigation.DoodleNavHost
import dev.enesky.doodle.app.ui.component.DoodleBottomBar
import dev.enesky.doodle.app.ui.component.DoodleSnackbarHost
import dev.enesky.doodle.app.ui.component.LocalSnackbarHostState
import dev.enesky.feature.splash.navigation.SplashDestination

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DoodleApp(
    modifier: Modifier = Modifier,
    appState: DoodleAppState = rememberDoodleAppState(),
) {
    DoodleTheme {
        CompositionLocalProvider(
            LocalSnackbarHostState provides appState.snackbarHostState,
        ) {
            // TODO: Add top bar with Doodle text on left and search icon on right
            Scaffold(
                topBar = {},
                bottomBar = {
                    AnimatedVisibility(
                        visible = appState.shouldShowBottomBar,
                        enter = BottomBarEnterTransition,
                        exit = BottomBarExitTransition,
                    ) {
                        DoodleBottomBar(
                            items = appState.bottomNavBarItems,
                            currentItem = appState.currentNavBarItem,
                            onNavigateToDestination = appState::navigate,
                        )
                    }
                },
                snackbarHost = {
                    DoodleSnackbarHost(
                        modifier = Modifier.windowInsetsPadding(
                            if (appState.shouldShowBottomBar) {
                                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                            } else {
                                WindowInsets.safeDrawing
                            },
                        ),
                        snackbarHostState = appState.snackbarHostState,
                    )
                },
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
            ) { innerPadding ->
                TransparentSystemBars()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DoodleTheme.colors.background,
                ) {
                    /**
                     * TODO: Loading screen
                     * if (appState.showLoading) {
                     *   LoadingWithTriangleDots()
                     * }
                     */

                    DoodleNavHost(
                        modifier = Modifier
                            .padding(paddingValues = innerPadding)
                            .consumeWindowInsets(paddingValues = innerPadding),
                        navController = appState.navController,
                        startDestination = SplashDestination,
                        onNavigateToDestination = appState::navigate,
                        onBackClick = appState::onBackClick,
                        onShowMessage = { message -> appState.showMessage(message) },
                    )
                }
            }
        }
    }
}

private val BottomBarEnterTransition = fadeIn() + expandVertically(expandFrom = Alignment.Top)
private val BottomBarExitTransition = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
