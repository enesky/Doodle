package dev.enesky.doodle.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.login.navigation.loginGraph
import dev.enesky.feature.main.navigation.homeGraph

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
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        loginGraph( )
        homeGraph()
    }
}
