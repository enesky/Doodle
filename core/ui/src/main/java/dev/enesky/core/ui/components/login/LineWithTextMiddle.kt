package dev.enesky.core.ui.components.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.design_system.theme.DoodleTheme

/**
 * Created by Enes Kamil YILMAZ on 30/11/2023
 */

@Composable
fun LineWithTextMiddle(
    modifier: Modifier = Modifier,
    text: String = "or",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DoodleTheme.spacing.xxSmall),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Divider(
            modifier = Modifier.weight(1f),
            color = DoodleTheme.colors.white,
            thickness = 1.dp,
        )
        Spacer(modifier = Modifier.width(DoodleTheme.spacing.xSmall))
        Text(
            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically),
            text = text,
            color = DoodleTheme.colors.white,
            style = DoodleTheme.typography.regular.h5,
        )
        Spacer(modifier = Modifier.width(DoodleTheme.spacing.xSmall))
        Divider(
            modifier = Modifier.weight(1f),
            color = DoodleTheme.colors.white,
            thickness = 1.dp,
        )
    }
}

@Preview
@Composable
private fun LineWithTextMiddlePreview() {
    LineWithTextMiddle()
}
