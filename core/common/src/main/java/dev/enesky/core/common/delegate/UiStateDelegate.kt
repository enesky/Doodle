package dev.enesky.core.common.delegate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

/**
 * Base interface for defining new UiState data classes
 **/
interface IUiState

/**
 * Base interface for ui state delegation
 **/
interface UiState<T> {

    /**
     * Main ui state flow
     **/
    val uiState: StateFlow<T>

    /**
     * Ui state value for ease of use
     **/
    val currentState: IUiState

    /**
     * Initialization function for first initialize of ui state
     *  Usage:
     *      override fun initialState(): LoginUiState = LoginUiState()
     **/
    fun initialState(): IUiState

    /**
     * Setter function for updating the ui state
     *  Usage:
     *      setState {
     *          copy(loading = false)
     *      }
     **/
    fun setState(reduce: T.() -> T)
}

/**
 * Delegation class for ui state usage
 **/
class UiStateDelegate<State : IUiState> : UiState<State> {

    private val initialState: State by lazy { initialState() }

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    override val uiState: StateFlow<State>
        get() = _uiState

    override val currentState: State = uiState.value

    override fun initialState(): State = initialState

    override fun setState(reduce: State.() -> State) {
        _uiState.update { currentState.reduce() }
    }
}
