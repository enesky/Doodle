package dev.enesky.core.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.enesky.core.design_system.DoodleTheme

/**
 * Created by Enes Kamil YILMAZ on 30/11/2023
 */

@Composable
fun ButtonWithImageAndText(
    modifier: Modifier = Modifier,
    imageResource: Int,
    text: String,
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

@Preview
@Composable
private fun GoogleButtonPreview() {
    ButtonWithImageAndText(
        imageResource = dev.enesky.core.design_system.R.drawable.ic_launcher,
        text = "Create new account",
    ) {}
}
