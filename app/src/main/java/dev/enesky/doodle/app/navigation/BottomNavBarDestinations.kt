package dev.enesky.doodle.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.explore.navigation.ExploreDestination
import dev.enesky.feature.main.navigation.HomeDestination
import dev.enesky.feature.my_lists.navigation.MyListsDestination
import dev.enesky.feature.settings.navigation.SettingsDestination

/**
 * Created by Enes Kamil YILMAZ on 05/12/2023
 */

enum class BottomNavBarDestinations(
    val destination: DoodleNavigationDestination,
    @DrawableRes val iconResourceId: Int,
    @StringRes val textResourceId: Int
) {
    Home(
        destination = HomeDestination,
        iconResourceId = dev.enesky.feature.login.R.drawable.ic_incognito,
        textResourceId = dev.enesky.core.design_system.R.string.label_home
    ),
    Explore(
        destination = ExploreDestination,
        iconResourceId = dev.enesky.feature.login.R.drawable.ic_incognito,
        textResourceId = dev.enesky.core.design_system.R.string.label_explore
    ),
    MyLists(
        destination = MyListsDestination,
        iconResourceId = dev.enesky.feature.login.R.drawable.ic_incognito,
        textResourceId = dev.enesky.core.design_system.R.string.label_mylists
    ),
    Settings(
        destination = SettingsDestination,
        iconResourceId = dev.enesky.feature.login.R.drawable.ic_incognito,
        textResourceId = dev.enesky.core.design_system.R.string.label_settings
    )
}