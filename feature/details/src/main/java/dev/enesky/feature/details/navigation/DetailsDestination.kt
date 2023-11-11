package dev.enesky.feature.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.android.material.snackbar.Snackbar
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.details.DetailsRoute

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

object DetailsDestination : DoodleNavigationDestination {
    override val route = "details_route"
    override val destination = "details_destination"

    const val idArgument = "id"
    val routeWithArguments = "$route/{$idArgument}}"

    fun createNavigationRoute(id: String) = "$route/$id"
}

fun NavGraphBuilder.detailsGraph(
    showSnackbar: () -> Unit
) = composable(route = DetailsDestination.routeWithArguments) {
    DetailsRoute(

    )
}
