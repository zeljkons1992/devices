package features.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import core.ui.ContractViewModel
import core.ui.ViewEvent
import core.ui.ViewSideEffect
import core.ui.ViewState
import features.home.domain.entities.Device
import features.home.domain.usecase.GetRemoteDevicesUseCase
import kotlinx.coroutines.launch

class RemoteDevicesViewModel(
    private val getRemoteDevicesUseCase: GetRemoteDevicesUseCase
) : ContractViewModel<RemoteDevicesState, RemoteDevicesEvent, RemoteDevicesSideEffect>(
    RemoteDevicesState.Idle
) {

    init {
        loadDevices()
    }

    override fun onEvent(event: RemoteDevicesEvent) {
        when (event) {
            is RemoteDevicesEvent.LoadDevices -> loadDevices()
        }
    }

    private fun loadDevices() {
        viewModelScope.launch {
            setState { RemoteDevicesState.Loading }
            try {
                val devices = getRemoteDevicesUseCase()
                setState { RemoteDevicesState.DevicesLoaded(devices) }
            } catch (e: Exception) {
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!")
                println(e.message)
                setState { RemoteDevicesState.Error(e.message) }
            }
        }
    }
}

sealed class RemoteDevicesState : ViewState {
    data object Idle : RemoteDevicesState()
    data object Loading : RemoteDevicesState()
    data class DevicesLoaded(val devices: List<Device>) : RemoteDevicesState()
    data class Error(val message: String?) : RemoteDevicesState()
}

sealed class RemoteDevicesEvent : ViewEvent {
    data object LoadDevices : RemoteDevicesEvent()
}

sealed class RemoteDevicesSideEffect : ViewSideEffect
