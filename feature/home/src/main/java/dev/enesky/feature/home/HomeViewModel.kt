package dev.enesky.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.enesky.core.common.consts.ErrorMessages
import dev.enesky.core.common.delegate.Event
import dev.enesky.core.common.delegate.EventDelegate
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate
import dev.enesky.core.domain.usecase.PopularAnimesUseCase
import dev.enesky.core.network.util.Resource
import dev.enesky.core.network.util.asResource
import dev.enesky.feature.home.helpers.HomeEvents
import dev.enesky.feature.home.helpers.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    fun getPopularAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            popularAnimesUseCase().asResource().onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateUiState { copy(loading = true) }
                    }

                    is Resource.Success -> {
                        updateUiState {
                            copy(
                                loading = false,
                                popularAnimes = resource.data,
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateUiState { copy(loading = false) }
                        triggerEvent {
                            HomeEvents.OnError(
                                resource.exception?.message ?: ErrorMessages.GENERAL_ERROR,
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }
}
