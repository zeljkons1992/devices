package features.home.domain.usecase

import features.home.domain.entities.Device
import features.home.domain.repository.DeviceRepository

class EditDeviceUseCase(private val deviceRepository: DeviceRepository) {
    suspend operator fun invoke(device: Device) = deviceRepository.editDevice(device)
}