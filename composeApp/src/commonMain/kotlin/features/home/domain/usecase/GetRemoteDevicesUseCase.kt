package features.home.domain.usecase

import features.home.domain.entities.Device
import features.home.domain.repository.DeviceRepository

class GetRemoteDevicesUseCase(private val deviceRepository: DeviceRepository) {
    suspend operator fun invoke(): List<Device> {
        return deviceRepository.getRemoteDevices()
    }
}