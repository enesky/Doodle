package dev.enesky.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.core.navigation.composableWithAnimation
import dev.enesky.feature.settings.SettingsRoute

/**
 * Created by Enes Kamil YILMAZ on 05/12/2023
 */

object SettingsDestination : DoodleNavigationDestination {
    override val route = "settings_route"
    override val destination = "settings_destination"
}

fun NavGraphBuilder.settingsGraph(
    showSnackbar: () -> Unit,
) = composableWithAnimation(route = SettingsDestination.route) {
    SettingsRoute()
}

