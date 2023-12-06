package dev.enesky.feature.explore.navigation

import androidx.navigation.NavGraphBuilder
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.core.navigation.composableWithAnimation
import dev.enesky.feature.explore.ExploreRoute

/**
 * Created by Enes Kamil YILMAZ on 06/12/2023
 */

object ExploreDestination : DoodleNavigationDestination {
    override val route = "explore_route"
    override val destination = "explore_destination"
}

fun NavGraphBuilder.exploreGraph(
    showSnackbar: () -> Unit,
) = composableWithAnimation(route = ExploreDestination.route) {
    ExploreRoute()
}