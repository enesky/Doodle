package dev.enesky.feature.login.signin

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import dev.enesky.core.data.LoginResult
import dev.enesky.core.data.LoginType
import dev.enesky.core.design_system.common.ButtonWithImageAndText
import dev.enesky.core.design_system.login.EmailAuthComponent
import dev.enesky.core.design_system.login.LineWithTextMiddle
import dev.enesky.core.design_system.login.LoginHeader
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.feature.login.R
import dev.enesky.feature.login.signin.helpers.SignInEvents
import dev.enesky.feature.login.signin.helpers.SignInUiState
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun SignInScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = koinViewModel(),
    navigateHome: () -> Unit,
    navigateSignUp: () -> Unit,
) {
    val signInUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                viewModel.signInWithGoogleResult(
                    intent = result.data ?: return@rememberLauncherForActivityResult,
                )
            } else {
                viewModel.setState {
                    copy(
                        loginType = LoginType.GOOGLE,
                        loginResult = LoginResult(
                            errorMessage = "Google Sign In Failed with resultCode= " + result.resultCode,
                        ),
                    )
                }
                Logger.error("SignInScreenRoute", "signInGoogleLauncher: ${result.resultCode}")
            }
        },
    )

    ObserveAsEvents(flow = viewModel.eventFlow) { signInEvents ->
        when (signInEvents) {
            is SignInEvents.OnError -> {
                Logger.debug("SignInScreen", "SignInEvents.OnError: ${signInEvents.errorMessage}")
            }
            is SignInEvents.NavigateToHome -> navigateHome()
            is SignInEvents.NavigateToSignUp -> navigateSignUp()
        }
    }

    DoodleTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = DoodleTheme.colors.background,
        ) {
            SignInScreen(
                modifier = Modifier.fillMaxWidth(),
                signInUiState = signInUiState,
                forgotPasswordButtonAction = { email ->
                    viewModel.forgotPassword(email)
                },
                onSignInWithEmail = { email, password ->
                    viewModel.signInWithEmailAndPassword(email, password)
                },
                onGoogleSignInClick = {
                    viewModel.signInWithGoogleLauncher(googleSignInLauncher)
                },
                onSignInAnonymouslyClick = viewModel::signInAnonymously,
                navigateSignUp = viewModel::navigateToSignUp,
            )
        }
    }
}

@Suppress("LongMethod")
@Composable
private fun SignInScreen(
    modifier: Modifier = Modifier,
    signInUiState: SignInUiState,
    forgotPasswordButtonAction: (email: String) -> Unit = {},
    onSignInWithEmail: (email: String, password: String) -> Unit = { _, _ -> },
    onGoogleSignInClick: () -> Unit = {},
    onSignInAnonymouslyClick: () -> Unit = {},
    navigateSignUp: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = DoodleTheme.spacing.xSmall,
                horizontal = DoodleTheme.spacing.xLarge,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoginHeader(
            headerMessage = stringResource(id = R.string.label_welcome_to),
        )

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.medium))

        EmailAuthComponent(
            signInButtonText = stringResource(R.string.label_sign_in),
            signInButtonAction = onSignInWithEmail,
            isForgotPasswordVisible = false,
            forgotPasswordButtonAction = forgotPasswordButtonAction,
            signUpButtonAction = navigateSignUp,
        )

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.large))

        LineWithTextMiddle()

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.large))

        ButtonWithImageAndText(
            imageResource = R.drawable.ic_google_logo,
            text = stringResource(R.string.label_sign_in_with_google),
        ) {
            onGoogleSignInClick()
        }

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.xxSmall))

        ButtonWithImageAndText(
            imageResource = R.drawable.ic_incognito,
            text = "Sign in anonymously",
        ) {
            onSignInAnonymouslyClick()
        }
    }
}

@PreviewUiMode
@Composable
private fun SignInScreenPreview() {
    DoodleTheme {
        SignInScreen(
            signInUiState = SignInUiState(),
        )
    }
}
