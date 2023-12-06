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
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.doodle.app.navigation.DoodleNavHost
import dev.enesky.doodle.app.ui.component.DoodleBottomBar
import dev.enesky.doodle.app.ui.component.DoodleSnackbarHost
import dev.enesky.doodle.app.ui.component.LocalSnackbarHostState
import dev.enesky.feature.login.manager.AuthManager
import dev.enesky.feature.home.navigation.HomeDestination
import org.koin.compose.koinInject

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DoodleApp(
    modifier: Modifier = Modifier,
    appState: DoodleAppState = rememberDoodleAppState(),
    // TODO: add new parameter -> systemBarsColor: Color = DoodleTheme.colors.primaryDark
) {
    DoodleTheme {
        CompositionLocalProvider(
            LocalSnackbarHostState provides appState.snackbarHostState,
        ) {
            Scaffold(
                bottomBar = {
                    AnimatedVisibility(
                        visible = appState.shouldShowBottomBar,
                        enter = BottomBarEnterTransition,
                        exit = BottomBarExitTransition
                    ) {
                        DoodleBottomBar(
                            destinations = appState.bottomNavBarDestinations,
                            currentDestination = appState.currentNavigationDestination,
                            onNavigateToDestination = appState::navigate
                        )
                    }
                },
                snackbarHost = {
                    DoodleSnackbarHost(
                        modifier = Modifier.windowInsetsPadding(
                            // if (appState.shouldShowBottomBar) {
                            //    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                            // } else {
                            WindowInsets.safeDrawing,
                            // }
                        ),
                        snackbarHostState = appState.snackbarHostState,
                    )
                },
                // Use this when you want to use all the screen
                // contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            ) { innerPadding ->

                /**
                 * Update the start destination according to the user's login status
                 */
                val authManager: AuthManager = koinInject<AuthManager>()
                if (authManager.isUserLoggedIn()) {
                    appState.startDestination = HomeDestination
                }

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
                    startDestination = appState.startDestination,
                    onNavigateToDestination = appState::navigate,
                    onBackClick = appState::onBackClick,
                    onShowMessage = { message -> appState.showMessage(message) },
                    // onSetSystemBarsColorTransparent = { appState.setSystemBarsColor(Color.Transparent) },
                    // onResetSystemBarsColor = { appState.setSystemBarsColor(systemBarsColor) }
                )
            }
        }
    }
}

private val BottomBarEnterTransition = fadeIn() + expandVertically(expandFrom = Alignment.Top)
private val BottomBarExitTransition = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
