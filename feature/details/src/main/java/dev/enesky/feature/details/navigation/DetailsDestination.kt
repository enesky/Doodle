package dev.enesky.feature.details.navigation

import androidx.navigation.NavGraphBuilder
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.core.navigation.composableWithAnimation
import dev.enesky.feature.details.DetailsRoute

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

object DetailsDestination : DoodleNavigationDestination {
    override val route = "details_route"
    override val destination = "details_destination"

    const val ID_ARGUMENT = "id"
    val routeWithArguments = "$route/{$ID_ARGUMENT}"

    fun createNavigationRoute(id: String) = "$route/$id"
}

fun NavGraphBuilder.detailsGraph(
    onShowMessage: (String) -> Unit,
) = composableWithAnimation(route = DetailsDestination.routeWithArguments) {
    DetailsRoute(
        animeId = it.arguments?.getString(DetailsDestination.ID_ARGUMENT) ?: "",
        onShowMessage = onShowMessage,
    )
}
