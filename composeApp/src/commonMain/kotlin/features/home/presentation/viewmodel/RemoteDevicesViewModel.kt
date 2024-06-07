package features.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import core.ui.ContractViewModel
import core.ui.ViewEvent
import core.ui.ViewSideEffect
import core.ui.ViewState
import features.home.domain.entities.Device
import features.home.domain.usecase.AddDeviceUseCase
import features.home.domain.usecase.DeleteDeviceUseCase
import features.home.domain.usecase.EditDeviceUseCase
import features.home.domain.usecase.GetRemoteDevicesUseCase
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RemoteDevicesViewModel(
    private val getRemoteDevicesUseCase: GetRemoteDevicesUseCase,
    private val addDeviceUseCase: AddDeviceUseCase,
    private val editDeviceUseCase: EditDeviceUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase,
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
            is RemoteDevicesEvent.DismissAddDeviceDialog -> setSideEffect { RemoteDevicesSideEffect.DismissAddDeviceDialog }
            is RemoteDevicesEvent.EditDevice -> editDevice(event.device)
            is RemoteDevicesEvent.ConfirmDeleteDevice -> deleteDevice(event.device)
            is RemoteDevicesEvent.DeleteDevice -> showDeleteDeviceDialog(event.device)
            is RemoteDevicesEvent.ShowEditDeviceDialog -> showEditDeviceDialog(event.device)
            is RemoteDevicesEvent.DismissDeleteDeviceDialog -> setSideEffect { RemoteDevicesSideEffect.DismissDeleteDeviceDialog }
            is RemoteDevicesEvent.DismissEditDeviceDialog -> setSideEffect { RemoteDevicesSideEffect.DismissEditDeviceDialog }
        }
    }

    private fun loadDevices() {
        viewModelScope.launch {
            setState { RemoteDevicesState.Loading }
            try {
                val devices = getRemoteDevicesUseCase()
                setState { RemoteDevicesState.DevicesLoaded(devices.toMutableList()) }
            } catch (e: Exception) {
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

    private fun editDevice(device: Device) {
        viewModelScope.launch {
            try {
                editDeviceUseCase(device)
                setState {
                    if (this is RemoteDevicesState.DevicesLoaded) {
                        val updatedDevices = this.devices.map { d -> if (d.id == device.id) device else d }
                        RemoteDevicesState.DevicesLoaded(updatedDevices)
                    } else {
                        RemoteDevicesState.DevicesLoaded(listOf(device))
                    }
                }
                setSideEffect { RemoteDevicesSideEffect.ShowMessage("Device updated successfully.") }
                setSideEffect { RemoteDevicesSideEffect.DismissEditDeviceDialog }
            } catch (e: Exception) {
                setState { RemoteDevicesState.Error(e.message) }
                setSideEffect { RemoteDevicesSideEffect.ShowMessage("Failed to update device.") }
            }
        }
    }

    private fun deleteDevice(device: Device) {
        viewModelScope.launch {
            try {
                deleteDeviceUseCase(device)
                setState {
                    if (this is RemoteDevicesState.DevicesLoaded) {
                        RemoteDevicesState.DevicesLoaded(this.devices - device)
                    } else {
                        RemoteDevicesState.DevicesLoaded(listOf(device))
                    }
                }
                setSideEffect { RemoteDevicesSideEffect.ShowMessage("Device deleted successfully.") }
                setSideEffect { RemoteDevicesSideEffect.DismissDeleteDeviceDialog }
            } catch (e: Exception) {
                setState { RemoteDevicesState.Error(e.message) }
                setSideEffect { RemoteDevicesSideEffect.ShowMessage("Failed to delete device.") }
            }
        }
    }

    private fun showEditDeviceDialog(device: Device) {
        setSideEffect { RemoteDevicesSideEffect.ShowEditDeviceDialog(device) }
    }

    private fun showDeleteDeviceDialog(device: Device) {
        setSideEffect { RemoteDevicesSideEffect.ShowDeleteDeviceDialog(device) }
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
    data class EditDevice(val device: Device) : RemoteDevicesEvent()
    data class DeleteDevice(val device: Device) : RemoteDevicesEvent()
    data class ConfirmDeleteDevice(val device: Device) : RemoteDevicesEvent()
    data object DismissDeleteDeviceDialog : RemoteDevicesEvent()
    data object DismissEditDeviceDialog : RemoteDevicesEvent()
    data class ShowEditDeviceDialog(val device: Device) : RemoteDevicesEvent()
}

sealed class RemoteDevicesSideEffect : ViewSideEffect {
    data object ShowAddDeviceDialog : RemoteDevicesSideEffect()
    data object DismissAddDeviceDialog : RemoteDevicesSideEffect()
    data class ShowMessage(val message: String) : RemoteDevicesSideEffect()
    data object DismissDeleteDeviceDialog : RemoteDevicesSideEffect()
    data object DismissEditDeviceDialog : RemoteDevicesSideEffect()
    data class ShowEditDeviceDialog(val device: Device) : RemoteDevicesSideEffect()
    data class ShowDeleteDeviceDialog(val device: Device) : RemoteDevicesSideEffect()
}
