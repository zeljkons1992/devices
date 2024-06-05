package features.home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.datastore.DataStoreRepository
import features.auth.presentaion.screens.login.LoginScreen
import features.home.presentation.dialogs.AddDeviceDialog
import features.home.presentation.viewmodel.HomeEvent
import features.home.presentation.viewmodel.HomeSideEffect
import features.home.presentation.viewmodel.HomeState
import features.home.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val dataStoreRepository: DataStoreRepository = koinInject()
        val viewModel: HomeViewModel = koinInject()
        val state by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        is HomeSideEffect.ShowAddDeviceDialog -> {
                            showDialog = true
                        }
                        is HomeSideEffect.DismissAddDeviceDialog -> {
                            showDialog = false
                        }
                        is HomeSideEffect.ShowMessage -> {
                            // Show message logic here (e.g., Snackbar)
                        }
                    }
                }
            }
        }

        Box (
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    val jwt = dataStoreRepository.getString("jwtToken")
                    println("JWT TOKEN :$jwt")
                    navigator?.push(HomeScreenDetails(number = 10))
                }) {
                    Text("GO")
                }
                Button(onClick = {
                    dataStoreRepository.deleteString("jwtToken")
                    navigator?.replace(LoginScreen())
                }) {
                    Text("LOGOUT")
                }
            }
            FloatingActionButton(
                onClick = { viewModel.onEvent(HomeEvent.ShowAddDeviceDialog)  },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Device")
            }

            when (state) {
                is HomeState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is HomeState.Error -> {
                    Text((state as HomeState.Error).message ?: "Unknown error", color = MaterialTheme.colors.error, modifier = Modifier.align(Alignment.Center))
                }
                else -> { /* No additional UI for other states */ }
            }
        }

        if (showDialog) {
            AddDeviceDialog(
                onDismiss = { viewModel.onEvent(HomeEvent.DismissAddDeviceDialog) },
                onSubmit = { device ->
                    viewModel.onEvent(HomeEvent.AddDevice(device))
                    viewModel.onEvent(HomeEvent.DismissAddDeviceDialog)
                }
            )
        }
    }
}

