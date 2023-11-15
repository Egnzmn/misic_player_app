package com.example.music_player_app.util.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseMVIViewModel<S : Any>(
    initialState: S
) : ViewModel(), MVIViewModel {

    val currentState: S
        get() = _uiState.value

    private val _uiAction: MutableSharedFlow<UIAction> = MutableSharedFlow()
    val uiAction = _uiAction.asSharedFlow()
    private val _uiEffect: Channel<UIEffect> = Channel()
    val uiEffect = _uiEffect.receiveAsFlow()
    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    init {
        init()
    }

    fun setState(state: S) {
        _uiState.value = state
    }

    fun setEffect(effect: UIEffect) {
        _uiEffect.trySend(effect)
    }

    fun setAction(action: UIAction) {
        _uiAction.tryEmit(action)
    }

    abstract fun handleAction(action: UIAction)

    private fun init() {
        viewModelScope.launch {
            uiAction.collect {
                handleAction(it)
            }
        }
    }
}
