package org.medeldevices.project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import features.auth.presentaion.screens.login.LoginScreen

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthScreen()
        }
    }
}

@Composable
fun AuthScreen() {
    val context = LocalContext.current
    val isLoggedIn = checkIfLoggedIn()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            Intent(context, MainActivity::class.java).also {
                context.startActivity(it)
            }
            (context as ComponentActivity).finish()
        }
    }

    if (!isLoggedIn) {
        MaterialTheme {
            Navigator(screen = LoginScreen())
        }
    }
}

private fun checkIfLoggedIn(): Boolean {
    // Implementirajte stvarnu logiku za proveru prijave korisnika
    return false
}
