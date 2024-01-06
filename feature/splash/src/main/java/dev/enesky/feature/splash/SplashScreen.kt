package dev.enesky.feature.splash

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.design_system.annotation.PreviewUiMode
import dev.enesky.core.design_system.components.CenteredBox
import dev.enesky.core.design_system.theme.DoodleTheme
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 04/01/2024
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SplashRoute(
    modifier: Modifier = Modifier,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    // TODO: Wait for permission result
    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        } else {
            null
        }

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    notificationPermissionState?.launchPermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

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

    SplashScreen(
        modifier = modifier,
        uiState = uiState,
    )
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
    uiState: SplashUiState = SplashUiState(),
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

@PreviewUiMode
@Composable
private fun SplashScreenPreview() {
    DoodleTheme {
        SplashScreen()
    }
}
