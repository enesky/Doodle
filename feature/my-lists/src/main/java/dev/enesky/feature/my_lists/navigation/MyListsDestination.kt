package dev.enesky.feature.my_lists.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.my_lists.MyListsRoute

/**
 * Created by Enes Kamil YILMAZ on 06/12/2023
 */

object MyListsDestination : DoodleNavigationDestination {
    override val route = "my_lists_route"
    override val destination = "my_lists_destination"
}

fun NavGraphBuilder.myListsGraph(
    showSnackbar: () -> Unit,
) = composable(route = MyListsDestination.route) {
    MyListsRoute()
}
