import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import com.preat.peekaboo.image.picker.toImageBitmap
import features.home.domain.entities.Device

@Composable
fun DeviceListItem(device: Device) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            device.image?.let {
                val bitmap = it.toImageBitmap()
                Image(
                    bitmap = bitmap,
                    contentDescription = "Device Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Name: ${device.name}", style = MaterialTheme.typography.h6)
                Text(text = "Status: ${device.status}", style = MaterialTheme.typography.body2)
                Text(text = "Category: ${device.category}", style = MaterialTheme.typography.body2)
                Text(text = "Locked: ${if (device.isLocked) "Yes" else "No"}", style = MaterialTheme.typography.body2)
                Text(text = "Inventory Number: ${device.inventoryNumber}", style = MaterialTheme.typography.body2)
            }
        }
    }
}
