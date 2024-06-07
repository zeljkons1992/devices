import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import features.home.domain.entities.Device

@Composable
fun DeleteConfirmationDialog(device: Device, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Device") },
        text = { Text("Are you sure you want to delete this device?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
