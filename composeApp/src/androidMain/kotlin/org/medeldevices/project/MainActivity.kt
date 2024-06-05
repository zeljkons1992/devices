package org.medeldevices.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import com.example.cmppreference.AppContext
import core.navigation.MainScreen
import core.utils.datastore.DataStoreViewModel
import features.auth.presentaion.screens.login.LoginScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppContext.apply { set(this@MainActivity) }
        setContent {
            MaterialTheme {
                CheckIsUserLogged()
            }
        }
    }
}

@Composable
fun CheckIsUserLogged(viewModel: DataStoreViewModel = koinViewModel()) {
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkIfLoggedIn()
    }

    if (isUserLoggedIn) {
        MainScreen()
    } else {
        Navigator(LoginScreen())
    }
}

