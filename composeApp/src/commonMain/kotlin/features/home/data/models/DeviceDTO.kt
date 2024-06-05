package features.home.data.models

import features.home.domain.entities.Device
import kotlinx.serialization.Serializable

@Serializable
data class DeviceDTO(
    val id: String,
    val name: String,
    val status: String,
    val category: String,
    val isLocked: Boolean,
    val inventoryNumber: String,
    val image: String?
) {
    fun toDeviceEntity(): Device {
        return Device(
            id = id,
            name = name,
            status = status,
            category = category,
            isLocked = isLocked,
            inventoryNumber = inventoryNumber,
            image = image
        )
    }

    companion object {
        fun fromDeviceEntity(deviceEntity: Device): DeviceDTO {
            return DeviceDTO(
                id = deviceEntity.id,
                name = deviceEntity.name,
                status = deviceEntity.status,
                category = deviceEntity.category,
                isLocked = deviceEntity.isLocked,
                inventoryNumber = deviceEntity.inventoryNumber,
                image = deviceEntity.image
            )
        }
    }
}

fun DeviceDTO.toDomain(): Device {
    return this.toDeviceEntity()
}

fun Device.toDataModel(): DeviceDTO {
    return DeviceDTO.fromDeviceEntity(this)
}
