package dev.enesky.doodle.app.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import dev.enesky.core.design_system.theme.Icons
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.feature.explore.navigation.ExploreDestination
import dev.enesky.feature.home.navigation.HomeDestination
import dev.enesky.feature.my_lists.navigation.MyListsDestination
import dev.enesky.feature.settings.navigation.SettingsDestination

/**
 * Created by Enes Kamil YILMAZ on 05/12/2023
 */

enum class BottomNavBarItem(
    override val route: String,
    override val destination: String,
    val imageVector: ImageVector,
    @StringRes val textResourceId: Int,
) : DoodleNavigationDestination {
    Home(
        route = HomeDestination.route,
        destination = HomeDestination.destination,
        imageVector = Icons.Home,
        textResourceId = dev.enesky.core.design_system.R.string.label_home,
    ),
    Explore(
        route = ExploreDestination.route,
        destination = ExploreDestination.destination,
        imageVector = Icons.Explore,
        textResourceId = dev.enesky.core.design_system.R.string.label_explore,
    ),
    MyLists(
        route = MyListsDestination.route,
        destination = MyListsDestination.destination,
        imageVector = Icons.MyLists,
        textResourceId = dev.enesky.core.design_system.R.string.label_mylists,
    ),
    Settings(
        route = SettingsDestination.route,
        destination = SettingsDestination.destination,
        imageVector = Icons.Settings,
        textResourceId = dev.enesky.core.design_system.R.string.label_settings,
    ),
}
