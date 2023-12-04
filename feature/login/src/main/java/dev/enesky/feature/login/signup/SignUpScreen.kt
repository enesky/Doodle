package dev.enesky.feature.login.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.common.utils.Logger
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.design_system.DoodleTheme
import dev.enesky.core.design_system.annotation.PreviewUiMode
import dev.enesky.core.ui.login.EmailAuthComponent
import dev.enesky.core.ui.login.LoginHeader
import dev.enesky.feature.login.R
import dev.enesky.feature.login.signup.helpers.SignUpEvents
import dev.enesky.feature.login.signup.helpers.SignUpUiState
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 29/11/2023
 */

@Composable
fun SignUpScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val signUpUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.eventFlow) { event ->
        when (event) {
            is SignUpEvents.OnError -> {
                Logger.debug("SignUpScreen", "onError: ${event.errorMessage}")
            }

            is SignUpEvents.NavigateToHome -> onNavigateToHome()
            is SignUpEvents.NavigateToSignIn -> onNavigateToSignIn()
        }
    }

    DoodleTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = DoodleTheme.colors.background,
        ) {
            SignUpScreen(
                modifier = Modifier.fillMaxSize(),
                signUpUiState = signUpUiState,
                onSignUpWithEmail = { email, password ->
                    viewModel.signUpWithEmail(email, password)
                },
            )
        }
    }
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signUpUiState: SignUpUiState,
    onSignUpWithEmail: (email: String, password: String) -> Unit = { _, _ -> },
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = DoodleTheme.spacing.small,
                horizontal = DoodleTheme.spacing.large,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoginHeader(
            headerMessage = "Sign Up to ",
        )

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.medium))

        EmailAuthComponent(
            isForgotPasswordVisible = false,
            isSignUoButtonVisible = false,
            signInButtonText = stringResource(id = R.string.label_sign_up),
            signInButtonAction = onSignUpWithEmail,
        )

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.largest))
    }
}

@PreviewUiMode
@Composable
private fun SignUpScreenPreview() {
    DoodleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DoodleTheme.colors.background,
        ) {
            SignUpScreen(
                signUpUiState = SignUpUiState(),
            )
        }
    }
}
