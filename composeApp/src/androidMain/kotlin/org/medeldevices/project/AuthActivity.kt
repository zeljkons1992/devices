package org.medeldevices.project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import cafe.adriel.voyager.navigator.Navigator
import features.auth.presentaion.screens.login.LoginScreen

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isLoggedIn = checkIfLoggedIn()

        if (isLoggedIn) {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
            finish()
        } else {
            setContent {
                MaterialTheme {
                    Navigator(screen = LoginScreen())
                }
            }

        }
    }

    private fun checkIfLoggedIn(): Boolean {
        return false;
    }
}