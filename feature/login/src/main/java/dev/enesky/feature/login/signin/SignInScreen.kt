package dev.enesky.feature.login.signin

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.common.utils.Logger
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.data.LoginType
import dev.enesky.core.data.LoginResult
import dev.enesky.core.design_system.DoodleTheme
import dev.enesky.core.design_system.annotation.PreviewUiMode
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
                    viewModel.signInWithGoogle(googleSignInLauncher)
                },
                onSignInAnonymouslyClick = viewModel::signInAnonymously,
                navigateSignUp = navigateSignUp,
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
                vertical = DoodleTheme.spacing.small,
                horizontal = DoodleTheme.spacing.large,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoginHeader()

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.medium))

        SignInWithEmail(
            signInButtonAction = onSignInWithEmail,
            forgotPasswordButtonAction = forgotPasswordButtonAction,
            signUpButtonAction = navigateSignUp,
        )

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.largest))

        LineWithTextMiddle()

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.largest))

        SignInButtonWithLogo {
            onGoogleSignInClick()
        }

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.extraSmall))

        SignInButtonWithLogo(
            imageResource = R.drawable.ic_incognito,
            text = "Sign in anonymously",
        ) {
            onSignInAnonymouslyClick()
        }
    }
}

// TODO: Move it to design system or ui module
@Composable
fun LoginHeader(
    modifier: Modifier = Modifier,
    headerMessage: String = stringResource(R.string.label_welcome_to),
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = "App Logo",
        )

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.large))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
        ) {
            Column {
                Text(
                    text = headerMessage,
                    color = DoodleTheme.colors.text,
                    textAlign = TextAlign.Center,
                    style = DoodleTheme.typography.bold.h2,
                )
                Spacer(modifier = Modifier.height(3.dp))
            }

            Text(
                text = stringResource(R.string.label_doodle),
                color = DoodleTheme.colors.text,
                textAlign = TextAlign.Center,
                style = DoodleTheme.typography.pacifico.h2,
            )
        }
    }
}

// TODO: Move it to design system or ui module
@Suppress("LongMethod")
@Composable
fun SignInWithEmail(
    modifier: Modifier = Modifier,
    isForgotPasswordVisible: Boolean = true,
    forgotPasswordButtonAction: (email: String) -> Unit = {},
    signInButtonText: String = stringResource(R.string.label_sign_in),
    signInButtonAction: (email: String, password: String) -> Unit,
    signUpButtonText: String = stringResource(R.string.label_sign_up),
    signUpButtonAction: () -> Unit,
) {
    var email by remember { mutableStateOf(String.Empty) }
    var password by remember { mutableStateOf(String.Empty) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val maxPassLength = 7
    val isFormValid = remember {
        derivedStateOf { email.isNotBlank() && password.length >= maxPassLength }
    }
    var isEmailRequired by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = stringResource(R.string.label_email),
                    style = DoodleTheme.typography.regular.h4,
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            trailingIcon = {
                if (email.isNotBlank()) {
                    IconButton(onClick = { email = String.Empty }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = String.Empty,
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = DoodleTheme.colors.text,
                unfocusedBorderColor = DoodleTheme.colors.main,
                focusedLabelColor = DoodleTheme.colors.text,
                focusedBorderColor = DoodleTheme.colors.main,
                unfocusedPlaceholderColor = DoodleTheme.colors.main,
                unfocusedLabelColor = DoodleTheme.colors.text,
            ),
            isError = isEmailRequired,
        )
        Spacer(modifier = Modifier.height(DoodleTheme.spacing.small))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(R.string.label_password),
                    style = DoodleTheme.typography.regular.h4,
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    },
                ) {
                    // TODO add icon for password visible/invisible eye
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = DoodleTheme.colors.text,
                unfocusedBorderColor = DoodleTheme.colors.main,
                focusedLabelColor = DoodleTheme.colors.text,
                focusedBorderColor = DoodleTheme.colors.main,
                unfocusedPlaceholderColor = DoodleTheme.colors.main,
                unfocusedLabelColor = DoodleTheme.colors.text,
            ),
        )
        if (isForgotPasswordVisible) {
            Spacer(modifier = Modifier.height(DoodleTheme.spacing.extraSmall))
            TextButton(
                modifier = Modifier.padding(DoodleTheme.spacing.extraSmall),
                onClick = {
                    if (email.isEmpty()) {
                        isEmailRequired = true
                        // TODO: Add snackbar with error message -> pls fill the email section first
                        return@TextButton
                    }
                    isEmailRequired = false
                    forgotPasswordButtonAction(email)
                },
            ) {
                Text(
                    text = stringResource(R.string.label_forgot_password),
                    color = DoodleTheme.colors.text,
                    style = DoodleTheme.typography.regular.h6,
                )
            }
            Spacer(modifier = Modifier.height(DoodleTheme.spacing.extraSmall))
        } else {
            Spacer(modifier = Modifier.height(DoodleTheme.spacing.large))
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DoodleTheme.spacing.smallMedium),
            shape = RoundedCornerShape(DoodleTheme.spacing.medium),
            border = BorderStroke(
                width = DoodleTheme.spacing.border,
                color = DoodleTheme.colors.main,
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = DoodleTheme.colors.white,
            ),
            onClick = {
                signInButtonAction(email, password)
            },
        ) {
            Text(
                text = signInButtonText,
                color = DoodleTheme.colors.black,
                style = DoodleTheme.typography.regular.h5,
            )
        }
    }
}

// TODO: Move it to design system or ui module
@Composable
private fun SignInButtonWithLogo(
    imageResource: Int = R.drawable.ic_google_logo,
    text: String = "Sign in with Google",
    action: () -> Unit,
) {
    val imageWeight = 1.5f
    val textWeight = 3f

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DoodleTheme.spacing.smallMedium),
        shape = RoundedCornerShape(DoodleTheme.spacing.medium),
        border = BorderStroke(
            width = DoodleTheme.spacing.border,
            color = DoodleTheme.colors.main,
        ),
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = DoodleTheme.colors.white,
        ),
    ) {
        Image(
            modifier = Modifier
                .size(DoodleTheme.spacing.extraMedium)
                .weight(imageWeight),
            painter = painterResource(id = imageResource),
            contentDescription = "Google Logo",

        )
        Text(
            modifier = Modifier.weight(textWeight),
            text = text,
            color = DoodleTheme.colors.black,
            style = DoodleTheme.typography.regular.h5,
        )
    }
}

// TODO: Move it to design system or ui module
@Composable
private fun LineWithTextMiddle(
    text: String = "or",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DoodleTheme.spacing.extraSmall),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Divider(
            modifier = Modifier.weight(1f),
            color = DoodleTheme.colors.white,
            thickness = 1.dp,
        )
        Spacer(modifier = Modifier.width(DoodleTheme.spacing.small))
        Text(
            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically),
            text = text,
            color = DoodleTheme.colors.white,
            style = DoodleTheme.typography.regular.h5,
        )
        Spacer(modifier = Modifier.width(DoodleTheme.spacing.small))
        Divider(
            modifier = Modifier.weight(1f),
            color = DoodleTheme.colors.white,
            thickness = 1.dp,
        )
    }
}

@Preview
@Composable
private fun LoginHeaderPreview() {
    Surface {
        LoginHeader()
    }
}

@Preview
@Composable
private fun SignInWithEmailPreview() {
    Surface {
        SignInWithEmail(
            signInButtonAction = { _, _ -> },
            signUpButtonAction = {},
        )
    }
}

@Preview
@Composable
private fun GoogleButtonPreview() {
    SignInButtonWithLogo {}
}

@Preview
@Composable
private fun LineWithTextMiddlePreview() {
    LineWithTextMiddle()
}

@PreviewUiMode
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        signInUiState = SignInUiState(),
    )
}
