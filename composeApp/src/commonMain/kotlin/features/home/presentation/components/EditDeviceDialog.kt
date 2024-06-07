import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import features.home.domain.entities.Device

@Composable
fun EditDeviceDialog(device: Device, onSubmit: (Device) -> Unit, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf(device.name) }
    var status by remember { mutableStateOf(device.status) }
    var category by remember { mutableStateOf(device.category) }
    var isLocked by remember { mutableStateOf(device.isLocked) }
    var inventoryNumber by remember { mutableStateOf(device.inventoryNumber) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Device") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") })
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isLocked, onCheckedChange = { isLocked = it })
                    Text("Is Locked")
                }
                OutlinedTextField(value = inventoryNumber, onValueChange = { inventoryNumber = it }, label = { Text("Inventory Number") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSubmit(device.copy(name = name, status = status, category = category, isLocked = isLocked, inventoryNumber = inventoryNumber))
            }) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
