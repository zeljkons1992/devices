package features.home.data.models

import features.home.domain.entities.DeviceEntity
import kotlinx.serialization.Serializable

@Serializable
data class DeviceModel(
    val id: String,
    val name: String,
    val status: String,
    val category: String,
    val isLocked: Boolean,
    val inventoryNumber: String,
    val image: String?
) {
    fun toDeviceEntity(): DeviceEntity {
        return DeviceEntity(
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
        fun fromDeviceEntity(deviceEntity: DeviceEntity): DeviceModel {
            return DeviceModel(
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

fun DeviceModel.toDomain(): DeviceEntity {
    return this.toDeviceEntity()
}

fun DeviceEntity.toDataModel(): DeviceModel {
    return DeviceModel.fromDeviceEntity(this)
}
