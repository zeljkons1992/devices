package features.home.domain.entities

data class DeviceEntity(
    val id: String,
    val name: String,
    val status: String,
    val category: String,
    val isLocked: Boolean,
    val inventoryNumber: String,
    val image: String?
)