package dev.enesky.core.design_system.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.design_system.R
import dev.enesky.core.design_system.theme.DoodleTheme
import dev.enesky.core.design_system.theme.Icons

@Composable
fun Message(
    @StringRes messageResourceId: Int,
    @DrawableRes imageResourceId: Int? = null,
    modifier: Modifier = Modifier,
) {
    CenteredBox(modifier = modifier.padding(horizontal = DoodleTheme.spacing.xLarge)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(DoodleTheme.spacing.medium),
        ) {
            Image(
                modifier = Modifier.size(72.dp),
                painter = if (imageResourceId != null) {
                    painterResource(id = imageResourceId)
                } else {
                    rememberVectorPainter(Icons.Error)
                },
                contentDescription = stringResource(id = messageResourceId),
            )
            Text(
                text = stringResource(id = messageResourceId),
                style = DoodleTheme.typography.regular.h5,
                color = DoodleTheme.colors.white,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun MessagePreview() {
    DoodleTheme {
        Message(
            modifier = Modifier.fillMaxSize(),
            messageResourceId = R.string.label_no_anime_result,
        )
    }
}
