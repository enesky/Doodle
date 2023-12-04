package dev.enesky.core.design_system.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.design_system.R
import dev.enesky.core.design_system.theme.DoodleTheme

/**
 * Created by Enes Kamil YILMAZ on 30/11/2023
 */

@Suppress("LongMethod")
@Composable
fun EmailAuthComponent(
    modifier: Modifier = Modifier,
    isForgotPasswordVisible: Boolean = false,
    forgotPasswordButtonAction: (email: String) -> Unit = {},
    isSignUoButtonVisible: Boolean = true,
    signUpButtonAction: () -> Unit = {},
    signInButtonText: String = stringResource(R.string.label_sign_in),
    signInButtonAction: (email: String, password: String) -> Unit,
) {
    var email by rememberSaveable { mutableStateOf(String.Empty) }
    var password by rememberSaveable { mutableStateOf(String.Empty) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val maxPassLength = 7
    val isFormValid = remember {
        derivedStateOf { email.isNotBlank() && password.length >= maxPassLength }
    }
    var isEmailRequired by rememberSaveable { mutableStateOf(false) }

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

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.xSmall))

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
                if (password.isNotBlank()) {
                    IconButton(
                        onClick = {
                            isPasswordVisible = isPasswordVisible.not()
                        },
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            },
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
        )

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.xxSmall))

        if (isForgotPasswordVisible) { // TODO: Make it visible when users password is incorrect
            TextButton(
                modifier = Modifier.padding(DoodleTheme.spacing.xxSmall),
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
                    textDecoration = TextDecoration.Underline,
                )
            }
            Spacer(modifier = Modifier.height(DoodleTheme.spacing.xxSmall))
        }

        if (isSignUoButtonVisible) {
            TextButton(
                modifier = Modifier.padding(DoodleTheme.spacing.xxSmall),
                onClick = { signUpButtonAction() },
            ) {
                val spannableString = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                        ),
                    ) {
                        withStyle(
                            style = DoodleTheme.typography.bold.h6.toSpanStyle(),
                        ) {
                            append("Sign up")
                        }
                    }
                }
                Text(
                    text = spannableString,
                    color = DoodleTheme.colors.text,
                    style = DoodleTheme.typography.regular.h6,
                )
            }
        }

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.xxSmall))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DoodleTheme.spacing.small),
            shape = RoundedCornerShape(DoodleTheme.spacing.medium),
            border = BorderStroke(
                width = DoodleTheme.spacing.border,
                color = DoodleTheme.colors.main,
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = DoodleTheme.colors.white,
            ),
            enabled = isFormValid.value,
            onClick = {
                if (isFormValid.value) {
                    signInButtonAction(email, password)
                }
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

@Preview
@Composable
private fun SignInWithEmailPreview() {
    Surface {
        EmailAuthComponent(
            signInButtonText = stringResource(R.string.label_sign_in),
            signInButtonAction = { _, _ -> },
        )
    }
}
