package dev.enesky.doodle.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.home.navigation.HomeDestination
import dev.enesky.feature.settings.navigation.SettingsDestination

/**
 * Created by Enes Kamil YILMAZ on 05/12/2023
 */

enum class BottomNavBarDestinations(
    override val route: String,
    override val destination: String,
    @DrawableRes val iconResourceId: Int,
    @StringRes val textResourceId: Int
) : DoodleNavigationDestination {
    Home(
        route = HomeDestination.route,
        destination = HomeDestination.destination,
        iconResourceId = dev.enesky.feature.login.R.drawable.ic_incognito,
        textResourceId = dev.enesky.core.design_system.R.string.label_home
    ),
    /*Explore(
        route = SearchDestination.route,
        destination = SearchDestination.destination,
        iconResourceId = dev.enesky.feature.login.R.drawable.ic_incognito,
        textResourceId = dev.enesky.core.design_system.R.string.label_explore
    ),
    MyLists(
        route = WishlistDestination.route,
        destination = WishlistDestination.destination,
        iconResourceId = dev.enesky.feature.login.R.drawable.ic_incognito,
        textResourceId = dev.enesky.core.design_system.R.string.label_mylists
    ),*/
    Settings(
        route = SettingsDestination.route,
        destination = SettingsDestination.destination,
        iconResourceId = dev.enesky.feature.login.R.drawable.ic_incognito,
        textResourceId = dev.enesky.core.design_system.R.string.label_settings
    )
}