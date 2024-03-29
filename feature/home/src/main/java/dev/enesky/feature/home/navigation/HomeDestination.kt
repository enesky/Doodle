package dev.enesky.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.home.HomeRoute

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

object HomeDestination : DoodleNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph(
    onNavigateToDetailsDestination: (animeId: String) -> Unit,
    onShowMessage: (String) -> Unit,
) = composable(route = HomeDestination.route) {
    HomeRoute(
        onShowMessage = onShowMessage,
        onNavigateDetailsClick = onNavigateToDetailsDestination,
    )
}
