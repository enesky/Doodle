package dev.enesky.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.core.navigation.composableWithAnimation
import dev.enesky.feature.main.HomeRoute

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

object HomeDestination : DoodleNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph(
    onNavigateToDetailsDestination: (id: String) -> Unit,
) = composableWithAnimation(route = HomeDestination.route) {
    HomeRoute(
        onNavigateDetailsClick = onNavigateToDetailsDestination,
    )
}
