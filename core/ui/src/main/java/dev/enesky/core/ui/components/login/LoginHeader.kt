package dev.enesky.core.ui.components.login

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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.design_system.R
import dev.enesky.core.design_system.theme.DoodleTheme

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
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = "App Logo",
        )

        Spacer(modifier = Modifier.height(DoodleTheme.spacing.xLarge))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
        ) {
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = DoodleTheme.typography.bold.h2.toSpanStyle(),
                ) {
                    append(headerMessage)
                }
                append("  ")
                withStyle(
                    style = DoodleTheme.typography.pacifico.h1.toSpanStyle(),
                ) {
                    append(stringResource(R.string.label_doodle))
                }
            }
            Text(
                text = annotatedString,
                color = DoodleTheme.colors.text,
                textAlign = TextAlign.Center,
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
