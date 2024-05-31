package features.home.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class HomeScreen : Screen{

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Button(onClick = {
                navigator?.push(HomeScreenDetails(number = 10))
            }){
                Text("GO")
            }
        }
    }
}