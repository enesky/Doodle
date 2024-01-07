package dev.enesky.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.enesky.core.navigation.DoodleNavigationDestination
import dev.enesky.core.navigation.composableWithAnimation
import dev.enesky.feature.login.signin.SignInScreenRoute
import dev.enesky.feature.login.signup.SignUpScreenRoute

/**
 * Created by Enes Kamil YILMAZ on 10/11/2023
 */

object LoginDestination : DoodleNavigationDestination {
    override val route = "login_route"
    override val destination = "login_destination"
}

object SignInDestination : DoodleNavigationDestination {
    override val route = "sign_in_route"
    override val destination = "sign_in_destination"
}

object SignUpDestination : DoodleNavigationDestination {
    override val route = "sign_up_route"
    override val destination = "sign_up_destination"
}

fun NavGraphBuilder.loginGraph(
    navController: NavHostController,
    onNavigateToHomeDestination: () -> Unit,
) {
    navigation(
        route = LoginDestination.route,
        startDestination = SignInDestination.route,
    ) {
        composable(
            route = SignInDestination.route,
        ) {
            SignInScreenRoute(
                navigateHome = onNavigateToHomeDestination,
                navigateSignUp = {
                    navController.navigate(SignUpDestination.route)
                },
            )
        }
        composableWithAnimation(route = SignUpDestination.route) {
            SignUpScreenRoute(
                onNavigateToHome = onNavigateToHomeDestination,
                onNavigateToSignIn = {
                    navController.navigate(SignInDestination.route)
                },
            )
        }
    }
}
