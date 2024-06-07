package features.home.domain.usecase

import features.home.domain.entities.Device
import features.home.domain.repository.DeviceRepository

class DeleteDeviceUseCase(private val deviceRepository: DeviceRepository) {
    suspend operator fun invoke(device: Device) = deviceRepository.deleteDevice(device)
}