package dev.enesky.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.splash.SplashRoute

/**
 * Created by Enes Kamil YILMAZ on 04/01/2024
 */

object SplashDestination : DoodleNavigationDestination {
    override val route = "splash_route"
    override val destination = "splash_destination"
}

fun NavGraphBuilder.splashGraph(
    showSnackbar: () -> Unit,
) = composable(route = SplashDestination.route) {
    SplashRoute()
}
