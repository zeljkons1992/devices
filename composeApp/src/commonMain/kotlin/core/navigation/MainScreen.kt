package core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import features.auth.presentation.components.LoginContent
import features.home.presentation.screens.HomeTab
import features.profile.ProfileTab
import features.settings.SettingsTab


class MainScreen():Screen {
    @Composable
    override fun Content() {
        val tabs = listOf(HomeTab, ProfileTab, SettingsTab)

        TabNavigator(HomeTab) { tabNavigator ->
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        tabs.forEach { tab ->
                            val isSelected = tabNavigator.current == tab
                            BottomNavigationItem(
                                icon = { tab.options.icon?.let { Icon(it, contentDescription = tab.options.title) } },
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
                },
                modifier = Modifier.fillMaxSize()
            ) {
                CurrentTab()
            }
        }
    }
}
