/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.enesky.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by Enes Kamil YILMAZ on 07/11/2023
 */

/**
 * The best option for using one time events with Compose is using Channels.
 * When using states we need to make sure we reset the state after the event is consumed.
 * So it can be forgotten and not re-triggered.
 * When using shared flow it can't re-trigger the event and it can cause to step backs.
 * So in order to fix it we need to add replay cache to the shared flow. But it can cause memory leaks.
 * So directly using channels is the best option.
 *
 * How to use ObserveAsEvents:
 * Create a channel on ViewModel and expose it as a flow.
 * on ViewModel:
 * private val navigationChannel = Channel<NavigationEvent>()
 * val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()
 *
 * viewModelScope.launch {
 *    navigationChannel.send(NavigationEvent.To(route = "route"))
 * }
 *
 * on Composable:
 * ObserveAsEvents(viewModel.navigationEventsChannelFlow) { event ->
 *     when (event) {
 *         is NavigationEvent.To -> {
 *             navController.navigate(event.route)
 *         }
 *     }
 * }
 *
 * check: https://youtu.be/njchj9d_Lf8 for more info
 *
 * @param flow The flow to observe as events
 * @param onEvent The event callback
 */
@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) { // To make sure there are no losses in the event
                flow.collect(onEvent)
            }
        }
    }
}
