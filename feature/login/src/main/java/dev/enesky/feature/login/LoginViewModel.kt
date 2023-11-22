package dev.enesky.feature.login

import androidx.lifecycle.ViewModel
import dev.enesky.core.common.delegate.UiState
import dev.enesky.core.common.delegate.UiStateDelegate

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

class LoginViewModel : ViewModel(), UiState<LoginUiState> by UiStateDelegate() {

    init {
        println("@@@@@ LoginViewModel initialized")
    }

    fun test() {
        setState {
            copy(loading = true)
        }
    }
}
