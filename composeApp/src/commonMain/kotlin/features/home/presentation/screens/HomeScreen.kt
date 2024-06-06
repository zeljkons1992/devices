package features.home.presentation.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import core.utils.datastore.DataStoreRepository
import features.auth.presentaion.screens.login.LoginScreen
import features.home.presentation.components.OfflineDevicesTab
import features.home.presentation.components.ProfileTab
import features.home.presentation.components.RemoteDevicesTab
import features.home.presentation.dialogs.AddDeviceDialog
import features.home.presentation.viewmodel.HomeEvent
import features.home.presentation.viewmodel.HomeSideEffect
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
        val snackbarHostState = remember { SnackbarHostState() }

        val tabs = listOf(RemoteDevicesTab, OfflineDevicesTab, ProfileTab)

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
                            snackbarHostState.showSnackbar(sideEffect.message)
                        }
                    }
                }
            }
        }
        TabNavigator(RemoteDevicesTab) {tabNavigator ->
            Scaffold(

                topBar = {
                    TopAppBar(
                        title = { Text("Home") },
                        actions = {
                            IconButton(onClick = {
                                dataStoreRepository.deleteString("jwtToken")
                                navigator?.replace(LoginScreen())
                            }) {
                                Icon(Icons.Default.Settings, contentDescription = "Logout")
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomNavigation {
                        tabs.forEach { tab ->
                            val isSelected = tabNavigator.current == tab
                            BottomNavigationItem(
                                icon = {tab.options.icon?.let { Icon(it, contentDescription = tab.options.title) }},
                                label = { Text(tab.options.title) },
                                selected = isSelected,
                                onClick = {
                                    if(!isSelected) {
                                        tabNavigator.current = tab
                                    }
                                }
                            )
                        }
                    }
                }
            ) {
                CurrentTab()
//                Box(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    FloatingActionButton(
//                        onClick = { viewModel.onEvent(HomeEvent.ShowAddDeviceDialog) },
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(16.dp)
//                    ) {
//                        Icon(Icons.Default.Add, contentDescription = "Add Device")
//                    }
//
//                    when (state) {
//                        is HomeState.Loading -> {
//                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                        }
//
//                        is HomeState.Error -> {
//                            Text(
//                                (state as HomeState.Error).message ?: "Unknown error",
//                                color = MaterialTheme.colors.error,
//                                modifier = Modifier.align(Alignment.Center)
//                            )
//                        }
//
//                        else -> { /* No additional UI for other states */
//                        }
//                    }
//
//                    SnackbarHost(
//                        hostState = snackbarHostState,
//                        modifier = Modifier.align(Alignment.BottomCenter)
//                    )
//                }
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
}

