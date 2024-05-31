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
import features.auth.presentaion.screens.login.AuthScreen
import features.auth.presentaion.screens.login.LoginScreen

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAuthScreen()
        }
    }
}

@Composable
fun AndroidAuthScreen() {
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

    AuthScreen(isLoggedIn = isLoggedIn) {
        Intent(context, MainActivity::class.java).also {
            context.startActivity(it)
        }
        (context as ComponentActivity).finish()
    }
}

private fun checkIfLoggedIn(): Boolean {
    // Implement your actual logic to check if the user is logged in
    return false
}