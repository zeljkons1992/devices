package features.home.presentation.components

import DeviceListItem
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
        val scope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }
        val snackbarHostState = remember { SnackbarHostState() }


        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        is RemoteDevicesSideEffect.ShowAddDeviceDialog -> {
                            showDialog = true
                        }

                        is RemoteDevicesSideEffect.DismissAddDeviceDialog -> {
                            showDialog = false
                        }

                        is RemoteDevicesSideEffect.ShowMessage -> {
                            snackbarHostState.showSnackbar(sideEffect.message)
                        }
                    }
                }
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
                            DeviceListItem(device)
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
            if (showDialog) {
                AddDeviceDialog(
                    onDismiss = { viewModel.onEvent(RemoteDevicesEvent.DismissAddDeviceDialog) },
                    onSubmit = { device ->
                        viewModel.onEvent(RemoteDevicesEvent.AddDevice(device))
                        viewModel.onEvent(RemoteDevicesEvent.DismissAddDeviceDialog)
                    }
                )
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

