package features.home.presentation.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import features.home.domain.entities.Device

@Composable
fun AddDeviceDialog(onDismiss: () -> Unit, onSubmit: (Device) -> Unit) {
    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isLocked by remember { mutableStateOf(false) }
    var inventoryNumber by remember { mutableStateOf("") }
    var image by remember { mutableStateOf<ByteArray?>(null) }
    var imagePath by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                image = it
                //imagePath = SaveImage( it)
                println(it)
            }
        }
    )


    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Add New Device", style = MaterialTheme.typography.h6)
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
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            singleImagePicker.launch()
                        }
                    ) {
                        Text("Pick Single Image")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                image?.let {
                    Image(
                        bitmap = it.toImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )
                } ?: Text(text = "Nope")
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
                                Device(
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
