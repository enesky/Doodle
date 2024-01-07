package dev.enesky.doodle.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.details.navigation.DetailsDestination
import dev.enesky.feature.details.navigation.detailsGraph
import dev.enesky.feature.explore.navigation.exploreGraph
import dev.enesky.feature.home.navigation.HomeDestination
import dev.enesky.feature.home.navigation.homeGraph
import dev.enesky.feature.login.navigation.LoginDestination
import dev.enesky.feature.login.navigation.loginGraph
import dev.enesky.feature.my_lists.navigation.myListsGraph
import dev.enesky.feature.settings.navigation.settingsGraph
import dev.enesky.feature.splash.navigation.SplashDestination
import dev.enesky.feature.splash.navigation.splashGraph

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun DoodleNavHost(
    navController: NavHostController,
    startDestination: DoodleNavigationDestination,
    onNavigateToDestination: (DoodleNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route,
    ) {
        splashGraph(
            onNavigateToLoginScreen = {
                navController.navigate(
                    LoginDestination.route,
                    navOptions {
                        popUpTo(SplashDestination.route) {
                            inclusive = true
                        }
                    },
                )
            },
            onNavigateToHomeScreen = {
                navController.navigate(
                    HomeDestination.route,
                    navOptions {
                        popUpTo(SplashDestination.route) {
                            inclusive = true
                        }
                    },
                )
            },
        )
        loginGraph(
            navController = navController,
            onNavigateToHomeDestination = {
                onNavigateToDestination(
                    HomeDestination,
                    HomeDestination.route,
                )
            },
        )
        homeGraph(
            onNavigateToDetailsDestination = { animeId ->
                onNavigateToDestination(
                    DetailsDestination,
                    DetailsDestination.createNavigationRoute(id = animeId),
                )
            },
        )
        detailsGraph { onShowMessage("Snackbar") }
        exploreGraph { onShowMessage("Snackbar") }
        myListsGraph { onShowMessage("Snackbar") }
        settingsGraph { onShowMessage("Snackbar") }
    }
}
