package dev.enesky.doodle.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.details.navigation.DetailsDestination
import dev.enesky.feature.details.navigation.detailsGraph
import dev.enesky.feature.login.loginGraph
import dev.enesky.feature.login.manager.AuthManager
import dev.enesky.feature.main.navigation.HomeDestination
import dev.enesky.feature.main.navigation.homeGraph
import org.koin.compose.koinInject

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
    val authManager: AuthManager = koinInject<AuthManager>()
    var startDest = startDestination.route

    LaunchedEffect(key1 = authManager) {
        if (authManager.isUserLoggedIn()) {
            startDest = HomeDestination.route
            navController.navigate(HomeDestination.route) {
                popUpTo(startDestination.route) {
                    inclusive = true
                }
            }
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDest,
    ) {
        loginGraph(
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
        detailsGraph(
            showSnackbar = {
                onShowMessage("Snackbar")
            },
        )
    }
}
