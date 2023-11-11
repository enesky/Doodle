package dev.enesky.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.main.HomeRoute

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

object HomeDestination : DoodleNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph(
) = composable(route = HomeDestination.route) {
    HomeRoute(

    )
}
