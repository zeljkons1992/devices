package features.home.presentation.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import features.home.domain.entities.DeviceEntity

@Composable
fun AddDeviceDialog(onDismiss: () -> Unit, onSubmit: (DeviceEntity) -> Unit) {
    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isLocked by remember { mutableStateOf(false) }
    var inventoryNumber by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Add New features.home.domain.entities.Device", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    isError = isError && name.isEmpty()
                )
                OutlinedTextField(
                    value = status,
                    onValueChange = { status = it },
                    label = { Text("Status") },
                    isError = isError && status.isEmpty()
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    isError = isError && category.isEmpty()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Is Locked")
                    Checkbox(checked = isLocked, onCheckedChange = { isLocked = it })
                }
                OutlinedTextField(
                    value = inventoryNumber,
                    onValueChange = { inventoryNumber = it },
                    label = { Text("Inventory Number") },
                    isError = isError && inventoryNumber.isEmpty()
                )
                OutlinedTextField(
                    value = image,
                    onValueChange = { image = it },
                    label = { Text("Image URL") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        if (name.isNotEmpty() && status.isNotEmpty() && category.isNotEmpty() && inventoryNumber.isNotEmpty()) {
                            onSubmit(
                                DeviceEntity(
                                    id = "",
                                    name = name,
                                    status = status,
                                    category = category,
                                    isLocked = isLocked,
                                    inventoryNumber = inventoryNumber,
                                    image = image
                                )
                            )
                        } else {
                            isError = true
                        }
                    }) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}
