package features.home.domain.entities


data class Device(
    var id: String,
    val name: String,
    val status: String,
    val category: String,
    val isLocked: Boolean,
    val inventoryNumber: String,
    val image: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Device

        if (id != other.id) return false
        if (name != other.name) return false
        if (status != other.status) return false
        if (category != other.category) return false
        if (isLocked != other.isLocked) return false
        if (inventoryNumber != other.inventoryNumber) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + isLocked.hashCode()
        result = 31 * result + inventoryNumber.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}