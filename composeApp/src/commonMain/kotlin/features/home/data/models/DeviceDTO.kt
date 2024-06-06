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
    var image: String?
)

