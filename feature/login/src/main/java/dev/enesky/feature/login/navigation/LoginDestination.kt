package dev.enesky.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.login.LoginRoute

/**
 * Created by Enes Kamil YILMAZ on 10/11/2023
 */

object LoginDestination : DoodleNavigationDestination {
    override val route = "login_route"
    override val destination = "login_destination"
}

fun NavGraphBuilder.loginGraph(
    onNavigateToHomeDestination: () -> Unit,
) = composable(route = LoginDestination.route) {
    LoginRoute(
        onNavigateHomeClick = onNavigateToHomeDestination,
    )
}
