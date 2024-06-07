package features.home.domain.repository

import features.home.domain.entities.Device

interface DeviceRepository {
    suspend fun addDevice(device: Device)
    suspend fun getRemoteDevices() : List<Device>

    suspend fun editDevice(device: Device)

    suspend fun deleteDevice(device: Device)
}