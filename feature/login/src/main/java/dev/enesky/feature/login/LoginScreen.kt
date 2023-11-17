package dev.enesky.feature.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.enesky.core.design_system.DarkThemeColors
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun LoginRoute(
    onNavigateHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    LoginScreen(
        modifier = modifier,
        onNavigateHomeClick = onNavigateHomeClick,
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateHomeClick: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        LoginContent(
            modifier = Modifier.fillMaxWidth(),
            onNavigateHomeClick = onNavigateHomeClick,
        )
    }
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    onNavigateHomeClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isFormValid = remember {
        derivedStateOf { email.isNotBlank() && password.length >= 7 }
    }
    var needEmail by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkThemeColors.ebonyClay)
            .padding(
                vertical = 8.dp,
                horizontal = 32.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = "App Logo",
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Welcome to Doodle!",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email"
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            trailingIcon = {
                if (email.isNotBlank()) {
                    IconButton(onClick = { email = "" }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "",
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                unfocusedBorderColor = DarkThemeColors.blazeOrange,
                focusedLabelColor = Color.White,
                focusedBorderColor = DarkThemeColors.blazeOrange,
                unfocusedPlaceholderColor = DarkThemeColors.blazeOrange,
                unfocusedLabelColor = DarkThemeColors.white,
            ),
            isError = needEmail,

            )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    "Password",
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
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    // TODO add icon for password visible/invisible eye
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                unfocusedBorderColor = DarkThemeColors.blazeOrange,
                focusedLabelColor = Color.White,
                focusedBorderColor = DarkThemeColors.blazeOrange,
                unfocusedPlaceholderColor = DarkThemeColors.blazeOrange,
                unfocusedLabelColor = DarkThemeColors.white,
            ),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                // TODO: add login
            },
            enabled = isFormValid.value,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                width = 1.dp,
                color = DarkThemeColors.blazeOrange,
                )
        ) {
            Text(
                text = "Sign Up/In",
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // TODO: add google sign in

        Button(
            onClick = {
                // TODO: add google sign in
            },
            enabled = isFormValid.value,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Google Sign In",
                color = Color.White,
            )
        }



        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            modifier = Modifier.padding(4.dp),
            onClick = {
                // TODO: add sign in anonymously
                onNavigateHomeClick()
            },
        ) {
            Text(
                text = "Sign in anonymously",
                color = Color.White,
                textDecoration = TextDecoration.Underline,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            modifier = Modifier.padding(4.dp),
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
                text = "Forgot password?",
                color = Color.White,
                fontSize = 12.sp,
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onNavigateHomeClick = {},
    )
}
