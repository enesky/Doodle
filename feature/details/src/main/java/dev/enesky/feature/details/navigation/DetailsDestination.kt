package dev.enesky.feature.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.details.DetailsRoute

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

object DetailsDestination : DoodleNavigationDestination {
    override val route = "details_route"
    override val destination = "details_destination"
}

fun NavGraphBuilder.detailsGraph(
) = composable(route = DetailsDestination.route) {
    DetailsRoute(

    )
}
