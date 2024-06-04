package features.home.domain.repository

import features.home.domain.entities.DeviceEntity

interface DeviceRepository {
    suspend fun addDevice(device: DeviceEntity)
}