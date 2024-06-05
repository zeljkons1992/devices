package features.auth.presentaion.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import features.auth.data.models.User
import features.auth.presentaion.screens.login.LoginScreen
import features.auth.presentation.mvi.viewmodel.AuthEvent
import features.auth.presentation.mvi.viewmodel.AuthSideEffect
import features.auth.presentation.mvi.viewmodel.AuthState
import features.auth.presentation.mvi.viewmodel.AuthViewModel
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

class RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        registrationScreenContent()
    }
}

@Composable
fun registrationScreenContent(viewModel: AuthViewModel = koinInject() ) {

    val navigator = LocalNavigator.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.uiState.collectAsState()
    val sideEffect by viewModel.sideEffect.collectAsState(initial = null)


    LaunchedEffect(sideEffect) {
        when (sideEffect) {
            AuthSideEffect.NavigateToAnotherScreen ->
                navigator?.replace(LoginScreen())
            null -> {}
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = {email= it},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                val user = User(username,email,password)
                viewModel.onEvent(AuthEvent.RegisterUser(user))

            },
                modifier = Modifier.fillMaxWidth()){
                Text("Registration")
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
    }
}