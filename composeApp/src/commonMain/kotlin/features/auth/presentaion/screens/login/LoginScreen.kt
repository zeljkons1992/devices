package features.auth.presentaion.screens.login

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import features.auth.presentaion.components.LoginComponents


class LoginScreen :Screen {
    @Composable
    override fun Content() {
        LoginComponents()
    }
}

@Composable
fun AuthScreen(isLoggedIn: Boolean, onLoginSuccess: () -> Unit) {
    if (isLoggedIn) {
        onLoginSuccess()
    } else {
        MaterialTheme {
            Navigator(screen = LoginScreen())
        }
    }
}