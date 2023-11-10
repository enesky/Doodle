package dev.enesky.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.loodos.login.LoginScreenRoute

/**
 * Created by Enes Kamil YILMAZ on 10/11/2023
 */

const val LoginNavigationRoute = "login_route"

fun NavController.navigateLoginScreen(navOptions: NavOptions? = null) {
    this.navigate(LoginNavigationRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(navigateToHome: () -> Unit) {
    composable(LoginNavigationRoute) {
        LoginScreenRoute(navigateToHome = navigateToHome)
    }
}
