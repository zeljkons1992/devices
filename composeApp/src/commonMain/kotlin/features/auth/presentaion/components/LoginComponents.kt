package features.auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.navigation.MainScreen
import features.auth.data.models.User
import features.auth.presentaion.screens.login.LoginScreen
import features.auth.presentaion.screens.registration.RegistrationScreen
import features.auth.presentation.mvi.viewmodel.AuthEvent
import features.auth.presentation.mvi.viewmodel.AuthSideEffect
import features.auth.presentation.mvi.viewmodel.AuthState
import features.auth.presentation.mvi.viewmodel.AuthViewModel
import features.home.presentation.screens.HomeTab
import org.koin.compose.koinInject

@Composable
fun LoginContent(viewModel: AuthViewModel = koinInject()) {

    val navigator = LocalNavigator.currentOrThrow
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    val sideEffect by viewModel.sideEffect.collectAsState(initial = null)


    var currentScreen by remember { mutableStateOf<Screen>(Screen.Auth) }

    LaunchedEffect(sideEffect) {
        if (sideEffect == AuthSideEffect.NavigateToAnotherScreen) {
            navigator.push(MainScreen())
        }
    }

    when (currentScreen) {
        Screen.Auth -> LoginScreen() // Your authentication screen
        Screen.Main -> MainScreen()
    }


    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val user = User(email = email, password = password)
                    viewModel.onEvent(AuthEvent.LoginUser(user))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Don't have an account? ")
                ClickableText(
                    text = AnnotatedString("Register"),
                    onClick = {
                        navigator?.push(RegistrationScreen())
                    }
                )
            }
        }
    }
    when (state) {
        is AuthState.Loading -> CircularProgressIndicator()
        is AuthState.Error -> {
            Text(
                text = (state as AuthState.Error).error,
                color = MaterialTheme.colors.error
            )
        }

        else -> {}
    }


}

sealed class Screen {
    data object Auth : Screen()
    data object Main : Screen()
}