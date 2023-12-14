package dev.enesky.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.usecase.PopularAnimesUseCase
import dev.enesky.feature.home.helpers.HomeEvents
import dev.enesky.feature.home.helpers.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class HomeViewModel(
    private val popularAnimesUseCase: PopularAnimesUseCase,
) : ViewModel(),
    UiState<HomeUiState> by UiStateDelegate(initialState = { HomeUiState() }),
    Event<HomeEvents> by EventDelegate() {

    init {
        getPopularAnimes()
    }

    private fun getPopularAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            val popularAnimes = popularAnimesUseCase().cachedIn(viewModelScope)

            updateUiState {
                copy(
                    loading = false,
                    popularAnimes = popularAnimes,
                )
            }
        }
    }
}
