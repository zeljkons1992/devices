package core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ViewEvent
interface ViewState
interface ViewSideEffect

abstract class ContractViewModel<UiState : ViewState, Event : ViewEvent, SideEffect : ViewSideEffect>(
    initialState: UiState
) : ViewModel() {

    abstract fun onEvent(event: Event)

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val uiState: StateFlow<UiState> = _uiState

    private val _sideEffect: Channel<SideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    protected fun setState(reducer: UiState.() -> UiState) {
        _uiState.update(reducer)
    }

    protected fun setSideEffect(builder: () -> SideEffect) {
        viewModelScope.launch { _sideEffect.send(builder()) }
    }
}