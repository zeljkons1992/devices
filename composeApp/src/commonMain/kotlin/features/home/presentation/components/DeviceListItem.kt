import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import com.preat.peekaboo.image.picker.toImageBitmap
import features.home.domain.entities.Device

@Composable
fun DeviceListItem(
    device: Device,
    onEdit: (Device) -> Unit,
    onDelete: (Device) -> Unit
) {
    val backgroundColor = when {
        device.isLocked -> Color(0xFFFFEB3B)
        device.status == "Available" -> Color(0xFFC8E6C9)
        device.status == "Unavailable" -> Color(0xFFFFCDD2)
        else -> Color(0xFFFFFFFF)
    }
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .background(backgroundColor)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f)) {
                if (device.image != null) {
                    val bitmap = device.image.toImageBitmap()
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Device Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Placeholder Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = device.name,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = device.status,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = device.category,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = device.inventoryNumber,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            if (!device.isLocked) {
                Row {
                    IconButton(onClick = { onEdit(device) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Device")
                    }
                    IconButton(onClick = { onDelete(device) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Device")
                    }
                }
            }
        }
    }
}
