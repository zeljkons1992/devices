package features.home.presentation.components

import DeleteConfirmationDialog
import DeviceListItem
import EditDeviceDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import features.home.domain.entities.Device
import features.home.presentation.dialogs.AddDeviceDialog
import features.home.presentation.viewmodel.RemoteDevicesEvent
import features.home.presentation.viewmodel.RemoteDevicesSideEffect
import features.home.presentation.viewmodel.RemoteDevicesState
import features.home.presentation.viewmodel.RemoteDevicesViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

object RemoteDevicesTab : Tab {

    @Composable
    override fun Content() {
        val viewModel: RemoteDevicesViewModel = koinInject()
        val state by viewModel.uiState.collectAsState()
        val sideEffect = viewModel.sideEffect.collectAsState(initial = null).value
        val scope = rememberCoroutineScope()
        var showAddDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }
        var showEditDialog by remember { mutableStateOf(false) }
        val snackbarHostState = remember { SnackbarHostState() }
        var deviceToEdit by remember { mutableStateOf<Device?>(null) }
        var deviceToDelete by remember { mutableStateOf<Device?>(null) }


        LaunchedEffect(sideEffect) {

                    when (sideEffect) {
                        is RemoteDevicesSideEffect.ShowAddDeviceDialog -> {
                            showAddDialog = true
                        }

                        is RemoteDevicesSideEffect.DismissAddDeviceDialog -> {
                            showAddDialog = false
                        }

                        is RemoteDevicesSideEffect.ShowMessage -> {
                            snackbarHostState.showSnackbar(sideEffect.message)
                        }

                        is RemoteDevicesSideEffect.DismissDeleteDeviceDialog -> showDeleteDialog = false
                        is RemoteDevicesSideEffect.DismissEditDeviceDialog -> showEditDialog = false
                        is RemoteDevicesSideEffect.ShowDeleteDeviceDialog -> {
                            deviceToDelete = sideEffect.device
                            showDeleteDialog = true
                        }
                        is RemoteDevicesSideEffect.ShowEditDeviceDialog -> {
                            deviceToEdit = sideEffect.device
                            showEditDialog = true
                        }

                        else -> {}
                    }

        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (state) {
                is RemoteDevicesState.Loading -> Text("Loading...")
                is RemoteDevicesState.DevicesLoaded -> {
                    val devices = (state as RemoteDevicesState.DevicesLoaded).devices
                    LazyColumn(
                        contentPadding = PaddingValues(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(devices) { device ->
                            DeviceListItem(device, onEdit = { viewModel.onEvent(RemoteDevicesEvent.ShowEditDeviceDialog(device)) }, onDelete = { viewModel.onEvent(RemoteDevicesEvent.DeleteDevice(device)) })
                        }
                    }
                }

                is RemoteDevicesState.Error -> Text("Error: ${(state as RemoteDevicesState.Error).message}")
                else -> Text("No devices found")
            }
            FloatingActionButton(
                onClick = { viewModel.onEvent(RemoteDevicesEvent.ShowAddDeviceDialog) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(PaddingValues(16.dp, 16.dp,16.dp,76.dp))
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Device")
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 76.dp)
            )
            if (showAddDialog) {
                AddDeviceDialog(
                    onDismiss = { viewModel.onEvent(RemoteDevicesEvent.DismissAddDeviceDialog) },
                    onSubmit = { device ->
                        viewModel.onEvent(RemoteDevicesEvent.AddDevice(device))
                        viewModel.onEvent(RemoteDevicesEvent.DismissAddDeviceDialog)
                    }
                )
            }

            if (showEditDialog) {
                deviceToEdit?.let {
                    EditDeviceDialog(
                        device = it,
                        onSubmit = { updatedDevice ->
                            viewModel.onEvent(RemoteDevicesEvent.EditDevice(updatedDevice))
                        },
                        onDismiss = { viewModel.onEvent(RemoteDevicesEvent.DismissEditDeviceDialog) }
                    )
                }
            }

            if (showDeleteDialog) {
                deviceToDelete?.let {
                    DeleteConfirmationDialog(
                        device = it,
                        onConfirm = { viewModel.onEvent(RemoteDevicesEvent.ConfirmDeleteDevice(it)) },
                        onDismiss = { viewModel.onEvent(RemoteDevicesEvent.DismissDeleteDeviceDialog) }
                    )
                }
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Devices"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

}

