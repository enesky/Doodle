package dev.enesky.feature.splash

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.design_system.components.CenteredBox
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.feature.home.HomeScreen
import dev.enesky.feature.login.signin.SignInScreen
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 04/01/2024
 */

@Composable
fun SplashRoute(
    modifier: Modifier = Modifier,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.isUserLoggedIn) {
        true -> SplashToHomeScreen(modifier)
        false -> SplashToLoginScreen(modifier)
        null -> SplashScreen(modifier)
    }

    RequestPermissions()

    ObserveAsEvents(flow = viewModel.eventFlow) {
        when (it) {
            is SplashEvents.OnNavigateToLoginScreen -> {
                onNavigateToLoginScreen()
            }

            is SplashEvents.OnNavigateToHomeScreen -> {
                onNavigateToHomeScreen()
            }

            is SplashEvents.OnError -> TODO()
        }
    }
}

@Composable
private fun SplashToHomeScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        HomeScreen {}
        SplashScreen()
    }
}

@Composable
private fun SplashToLoginScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        SignInScreen {}
        SplashScreen()
    }
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = DoodleTheme.colors.background.copy(alpha = 0.5f),
    ) {
        CenteredBox(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = dev.enesky.core.design_system.R.drawable.doodle_icon),
                tint = DoodleTheme.colors.white,
                contentDescription = "Doodle Icon",
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestPermissions() {
    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    } else {
        return
    }

    LaunchedEffect(Unit) {
        when(permissionState.status) {
            PermissionStatus.Granted -> return@LaunchedEffect
            PermissionStatus.Denied(shouldShowRationale = true) -> {
                // Denied one time but can be requested again -> Todo: Explain why you need this permission
                permissionState.launchPermissionRequest()
            }
            else -> permissionState.launchPermissionRequest()
        }
    }
}

@Preview
@Composable
private fun SplashToHomeScreenPreview() {
    DoodleTheme {
        SplashToHomeScreen()
    }
}

@Preview
@Composable
private fun SplashToLoginScreenPreview() {
    DoodleTheme {
        SplashToLoginScreen()
    }
}
