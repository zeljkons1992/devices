package features.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import core.ui.ContractViewModel
import core.ui.ViewEvent
import core.ui.ViewSideEffect
import core.ui.ViewState
import features.home.domain.entities.Device
import features.home.domain.usecase.AddDeviceUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val addDeviceUseCase: AddDeviceUseCase
) : ContractViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState.Idle) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.AddDevice -> addDevice(event.device)
            is HomeEvent.ShowAddDeviceDialog -> setSideEffect { HomeSideEffect.ShowAddDeviceDialog }
            is HomeEvent.DismissAddDeviceDialog -> setSideEffect { HomeSideEffect.DismissAddDeviceDialog }
        }
    }
    private fun addDevice(device: Device) {
        viewModelScope.launch() {
            setState { HomeState.Loading }
            try {
                addDeviceUseCase(device)
                setState { HomeState.Idle }
                setSideEffect { HomeSideEffect.DismissAddDeviceDialog }
                setSideEffect { HomeSideEffect.ShowMessage("Device added successfully.") }
            } catch (e: Exception) {
                setState { HomeState.Error(e.message) }
                setSideEffect { HomeSideEffect.ShowMessage("Failed to add device.") }
            }
        }
    }
}

sealed class HomeEvent : ViewEvent {
    data class AddDevice(val device: Device) : HomeEvent()
    data object ShowAddDeviceDialog : HomeEvent()
    data object DismissAddDeviceDialog : HomeEvent()
}

sealed class HomeState : ViewState {
    data object Idle : HomeState()
    data object Loading : HomeState()
    data class Error(val message: String?) : HomeState()
}
sealed class HomeSideEffect : ViewSideEffect {
    data object ShowAddDeviceDialog : HomeSideEffect()
    data object DismissAddDeviceDialog : HomeSideEffect()
    data class ShowMessage(val message: String) : HomeSideEffect()
}
