package dev.enesky.feature.login

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.design_system.DoodleTheme
import dev.enesky.core.design_system.annotation.PreviewUiMode
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun LoginScreenRoute(
    onNavigateHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val loginUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                viewModel.signInGoogleWithIntent(
                    intent = result.data ?: return@rememberLauncherForActivityResult,
                )
            }
        },
    )

    LoginScreen(
        modifier = modifier,
        loginUiState = loginUiState,
        onNavigateHomeClick = onNavigateHomeClick,
        onGoogleSignInClick = {
            viewModel.clickSignInWithGoogle(launcher = launcher)
        },
    )
}

@Composable
private fun LoginScreen(
    loginUiState: LoginUiState,
    modifier: Modifier = Modifier,
    onNavigateHomeClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
) {
    DoodleTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = DoodleTheme.colors.background,
        ) {
            LoginContent(
                modifier = Modifier.fillMaxWidth(),
                loginUiState = loginUiState,
                onNavigateHomeClick = onNavigateHomeClick,
                onGoogleSignInClick = onGoogleSignInClick,
            )
        }
    }
}

@Suppress("LongMethod")
@Composable
private fun LoginContent(
    loginUiState: LoginUiState,
    modifier: Modifier = Modifier,
    onNavigateHomeClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
) {
    var email by remember { mutableStateOf(String.Empty) }
    var password by remember { mutableStateOf(String.Empty) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val maxPassLength = 7
    val isFormValid = remember {
        derivedStateOf { email.isNotBlank() && password.length >= maxPassLength }
    }
    var needEmail by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
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
            isError = needEmail,
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
            visualTransformation =
            if (isPasswordVisible) {
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
        Spacer(modifier = Modifier.height(DoodleTheme.spacing.large))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DoodleTheme.spacing.smallMedium),
            enabled = isFormValid.value,
            shape = RoundedCornerShape(DoodleTheme.spacing.medium),
            border = BorderStroke(
                width = DoodleTheme.spacing.border,
                color = DoodleTheme.colors.main,
            ),
            onClick = {
                // TODO: add login
            },
        ) {
            Text(
                text = stringResource(R.string.label_login),
                color = DoodleTheme.colors.text,
                style = DoodleTheme.typography.regular.h4,
            )
        }
        Spacer(modifier = Modifier.height(DoodleTheme.spacing.medium))

        GoogleButton {
            onGoogleSignInClick()
        }

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.medium))
        TextButton(
            modifier = Modifier.padding(DoodleTheme.spacing.extraSmall),
            onClick = {
                // TODO: add sign in anonymously
                onNavigateHomeClick()
            },
        ) {
            Text(
                text = stringResource(R.string.label_sign_in_anonymously),
                color = DoodleTheme.colors.text,
                textDecoration = TextDecoration.Underline,
                style = DoodleTheme.typography.regular.h5,
            )
        }
        Spacer(modifier = Modifier.height(DoodleTheme.spacing.medium))
        TextButton(
            modifier = Modifier.padding(DoodleTheme.spacing.extraSmall),
            onClick = {
                if (email.isEmpty()) {
                    needEmail = true
                    return@TextButton
                }
                needEmail = false
                // TODO add forgot password
            },
        ) {
            Text(
                text = stringResource(R.string.label_forgot_password),
                color = DoodleTheme.colors.text,
                style = DoodleTheme.typography.regular.h6,
            )
        }
    }
}

@Composable
private fun LoginHeader() {
    Column(
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
                    text = stringResource(R.string.label_welcome_to),
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

@Composable
private fun GoogleButton(
    onGoogleSignInClick: () -> Unit,
) {
    TextButton(onClick = onGoogleSignInClick) {
        Text(
            text = "Google Sign In",
            color = DoodleTheme.colors.text,
            style = DoodleTheme.typography.regular.h3,
        )
    }
}

@Preview
@Composable
private fun GoogleButtonPreview() {
    GoogleButton {}
}

@PreviewUiMode
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        loginUiState = LoginUiState(),
        onNavigateHomeClick = {},
        onGoogleSignInClick = {},
    )
}
