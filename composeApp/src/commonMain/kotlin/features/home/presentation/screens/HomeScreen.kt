package features.home.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.datastore.DataStoreRepository
import features.auth.presentaion.screens.login.LoginScreen
import org.koin.compose.koinInject

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val dataStoreRepository: DataStoreRepository = koinInject()
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
    }
}

