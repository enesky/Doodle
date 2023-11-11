package dev.enesky.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel()
) {
    LoginScreen(
        modifier = modifier
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier
) {
    LoginContent(
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Welcome to Login Screen",
                style = MaterialTheme.typography.headlineLarge,
            )
            Button(
                modifier = Modifier.padding(32.dp),
                onClick = {
                    // TODO: Add navigation to Home Screen
                },
            ) {
                Text(text = "Navigate to Home", color = Color.White)
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}
