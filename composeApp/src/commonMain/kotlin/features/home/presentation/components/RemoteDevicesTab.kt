package features.home.presentation.components

import DeviceListItem
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import features.home.domain.entities.Device
import features.home.presentation.viewmodel.RemoteDevicesState
import features.home.presentation.viewmodel.RemoteDevicesViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

object RemoteDevicesTab : Tab {

    @Composable
    override fun Content() {
        val viewModel: RemoteDevicesViewModel = koinInject()
        val state by viewModel.uiState.collectAsState()

        when (state) {
            is RemoteDevicesState.Loading -> Text("Loading...")
            is RemoteDevicesState.DevicesLoaded -> {
                val devices = (state as RemoteDevicesState.DevicesLoaded).devices
                LazyColumn(
                    contentPadding = PaddingValues(Dp(10F)),
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