package features.home.data.mappers

import core.utils.imageConverter.ImageStorage
import features.home.data.models.DeviceDTO
import features.home.domain.entities.Device

suspend fun Device.toDeviceDTO(imageStorage: ImageStorage): DeviceDTO {
    return DeviceDTO(
        id = id,
        name = name,
        status = status,
        category = category,
        isLocked = isLocked,
        inventoryNumber = inventoryNumber,
        image = image?.let { imageStorage.saveImage(it) }
    )
}

suspend fun DeviceDTO.toDevice(imageStorage: ImageStorage): Device {
    return Device(
        id = id,
        name = name,
        status = status,
        category = category,
        isLocked = isLocked,
        inventoryNumber = inventoryNumber,
        image = image?.let { imageStorage.getImage(it) }
    )
}