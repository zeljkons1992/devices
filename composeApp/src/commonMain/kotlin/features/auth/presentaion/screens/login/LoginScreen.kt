package features.auth.presentaion.screens.login

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import features.auth.presentaion.components.LoginComponents

class LoginScreen :Screen {
    @Composable
    override fun Content() {
        LoginComponents()
    }
}