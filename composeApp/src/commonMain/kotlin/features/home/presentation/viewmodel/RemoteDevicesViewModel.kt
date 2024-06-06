package features.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import core.ui.ContractViewModel
import core.ui.ViewEvent
import core.ui.ViewSideEffect
import core.ui.ViewState
import features.home.domain.entities.Device
import features.home.domain.usecase.AddDeviceUseCase
import features.home.domain.usecase.GetRemoteDevicesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RemoteDevicesViewModel(
    private val getRemoteDevicesUseCase: GetRemoteDevicesUseCase,
    private val addDeviceUseCase: AddDeviceUseCase
) : ContractViewModel<RemoteDevicesState, RemoteDevicesEvent, RemoteDevicesSideEffect>(
    RemoteDevicesState.Idle
) {
    init {
        loadDevices()
    }

    override fun onEvent(event: RemoteDevicesEvent) {
        when (event) {
            is RemoteDevicesEvent.LoadDevices -> loadDevices()
            is RemoteDevicesEvent.ShowAddDeviceDialog -> setSideEffect { RemoteDevicesSideEffect.ShowAddDeviceDialog }
            is RemoteDevicesEvent.AddDevice -> addDevice(event.device)
            RemoteDevicesEvent.DismissAddDeviceDialog -> setSideEffect { RemoteDevicesSideEffect.DismissAddDeviceDialog }
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

    private fun addDevice(device: Device) {
        viewModelScope.launch() {
            try {
                addDeviceUseCase(device)
                setState {
                    if (this is RemoteDevicesState.DevicesLoaded) {
                        RemoteDevicesState.DevicesLoaded(this.devices + device)
                    } else {
                        RemoteDevicesState.DevicesLoaded(listOf(device))
                    }
                }
                setSideEffect { RemoteDevicesSideEffect.DismissAddDeviceDialog }
                setSideEffect { RemoteDevicesSideEffect.ShowMessage("Device added successfully.") }
            } catch (e: Exception) {
                setState { RemoteDevicesState.Error(e.message) }
                setSideEffect { RemoteDevicesSideEffect.ShowMessage("Failed to add device.") }
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
    data object ShowAddDeviceDialog : RemoteDevicesEvent()
    data object DismissAddDeviceDialog : RemoteDevicesEvent()

    data class AddDevice(val device: Device) : RemoteDevicesEvent()
}

sealed class RemoteDevicesSideEffect : ViewSideEffect {
    data object ShowAddDeviceDialog : RemoteDevicesSideEffect()
    data object DismissAddDeviceDialog : RemoteDevicesSideEffect()
    data class ShowMessage(val message: String) : RemoteDevicesSideEffect()
}
