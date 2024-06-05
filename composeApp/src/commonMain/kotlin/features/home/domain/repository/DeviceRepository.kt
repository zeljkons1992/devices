package features.home.domain.repository

import features.home.domain.entities.Device

interface DeviceRepository {
    suspend fun addDevice(device: Device)
}