package dev.enesky.feature.my_lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.enesky.core.design_system.annotation.PreviewUiMode
import dev.enesky.core.design_system.theme.DoodleTheme

/**
 * Created by Enes Kamil YILMAZ on 06/12/2023
 */

@Composable
fun MyListsRoute(
    modifier: Modifier = Modifier,
) {
    MyListsScreen(modifier)
}

@Composable
private fun MyListsScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = DoodleTheme.colors.background,
    ) {
        MyListsContent(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun MyListsContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Welcome to MyLists Screen",
            style = DoodleTheme.typography.regular.h3,
        )
    }
}

@PreviewUiMode
@Composable
private fun MyListsScreenPreview() {
    DoodleTheme {
        MyListsScreen()
    }
}
