package dev.enesky.doodle.app.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.doodle.app.navigation.BottomNavBarItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun rememberDoodleAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) = remember(
    snackbarHostState,
    coroutineScope,
    navController,
) {
    DoodleAppState(
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope,
        navController = navController,
    )
}

@Stable
class DoodleAppState(
    val snackbarHostState: SnackbarHostState,
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {

    // Snackbar related properties and functions
    init {
        coroutineScope.launch {
            snackbarMessages.collect { messages ->
                if (messages.isNotEmpty()) {
                    val message = messages.first()
                    snackbarHostState.showSnackbar(
                        message = message,
                        withDismissAction = true,
                    )
                    snackbarMessages.update { messageList ->
                        messageList.filterNot { it == message }
                    }
                }
            }
        }
    }

    private val snackbarMessages = MutableStateFlow<List<String>>(emptyList())

    fun showMessage(message: String) = snackbarMessages.update { it + message }

    // Bottom nav bar related properties and functions
    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route == currentNavBarItem.route

    val bottomNavBarItems = BottomNavBarItem.entries.toTypedArray()

    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    private var _currentNavBarItem by mutableStateOf(bottomNavBarItems.first())
    val currentNavBarItem: BottomNavBarItem
        @Composable get() {
            bottomNavBarItems.firstOrNull { it.route == currentDestination?.route }
                ?.let { _currentNavBarItem = it }
            return _currentNavBarItem
        }

    // Navigation related functions
    /**
     * UI logic for navigating to a particular destination in the app. The NavigationOptions to
     * navigate with are based on the type of destination, which could be a top level destination or
     * just a regular destination.
     *
     * Top level destinations have only one copy of the destination of the back stack, and save and
     * restore state whenever you navigate to and from it.
     * Regular destinations can have multiple copies in the back stack and state isn't saved nor
     * restored.
     *
     * @param destination The [DoodleNavigationDestination] the app needs to navigate to.
     * @param route Optional route to navigate to in case the destination contains arguments.
     */
    fun navigate(destination: DoodleNavigationDestination, route: String? = null) =
        with(navController) {
            navigate(route ?: destination.route) {
                if (destination is BottomNavBarItem) {
                    // Pop up to the start destination of the graph to avoid building up
                    // a large stack of destinations on the back stack as users select items.
                    popUpTo(graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when reselecting the same item.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item.
                    restoreState = true
                }
            }
        }

    fun onBackClick() = navController.popBackStack()
}
