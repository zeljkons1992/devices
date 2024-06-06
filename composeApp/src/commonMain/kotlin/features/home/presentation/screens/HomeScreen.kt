package features.home.presentation.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import core.utils.datastore.DataStoreRepository
import features.auth.presentaion.screens.login.LoginScreen
import features.home.presentation.components.OfflineDevicesTab
import features.home.presentation.components.ProfileTab
import features.home.presentation.components.RemoteDevicesTab
import org.koin.compose.koinInject

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val dataStoreRepository: DataStoreRepository = koinInject()

        val tabs = listOf(RemoteDevicesTab, OfflineDevicesTab, ProfileTab)

        TabNavigator(RemoteDevicesTab) { tabNavigator ->
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
                                icon = {
                                    tab.options.icon?.let {
                                        Icon(
                                            it,
                                            contentDescription = tab.options.title
                                        )
                                    }
                                },
                                label = { Text(tab.options.title) },
                                selected = isSelected,
                                onClick = {
                                    if (!isSelected) {
                                        tabNavigator.current = tab
                                    }
                                }
                            )
                        }
                    }
                }
            ) {
                CurrentTab()
            }
        }
    }
}

