package features.auth.presentaion.screens.login

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import features.auth.presentaion.screens.registration.RegistrationScreen

import features.auth.presentation.components.LoginContent


class LoginScreen :Screen {
    @Composable
    override fun Content() {
        LoginContent()
    }
}

