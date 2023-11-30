package dev.enesky.core.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.design_system.DoodleTheme
import dev.enesky.core.ui.R

/**
 * Created by Enes Kamil YILMAZ on 30/11/2023
 */

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier,
    headerMessage: String = stringResource(R.string.lorem_ipsum_short),
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = dev.enesky.core.design_system.R.drawable.ic_launcher),
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

@Preview
@Composable
private fun LoginHeaderPreview() {
    Surface {
        LoginHeader()
    }
}
