package features.home.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import core.utils.datastore.DataStoreRepository
import org.koin.compose.koinInject

object HomeTab : Tab {

    @Composable
    override fun Content( ) {
        val localNavigator = LocalNavigator.current
        val dataStoreRepository: DataStoreRepository= koinInject()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                val jwt =dataStoreRepository.getString("jwtToken")
                println("JWT TOKEN :$jwt")
                // Example of navigation within the tab
                // localNavigator?.push(HomeScreenDetails(number = 10))
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
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
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
