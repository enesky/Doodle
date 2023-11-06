package dev.enesky.doodle.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import dev.enesky.doodle.R
import dev.enesky.doodle.app.ui.theme.DoodleTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    // private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoodleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(
    modifier: Modifier = Modifier,
) {
    /*
    val mainViewModel: MainViewModel?
    if (!LocalInspectionMode.current) {
        // We're _not_ executing in an Android Studio Preview.
        mainViewModel = koinViewModel()
        mainViewModel.getPopularAnimes()
    } else {
        mainViewModel = null
    }
     */

    val viewModel = getViewModel<MainViewModel>()
    viewModel.getPopularAnimes()
    val animes = viewModel.popularAnimes.collectAsLazyPagingItems()

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.basicMarquee(
                    iterations = Int.MAX_VALUE
                ),
                text = stringResource(id = R.string.welcome, stringResource(R.string.app_name)),
                style = MaterialTheme.typography.headlineLarge,
            )
            Button(
                modifier = Modifier.padding(32.dp),
                onClick = {
                    print(animes)
                }
            ) {
                Text(text = "Request it", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    DoodleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Greeting()
        }
    }
}
