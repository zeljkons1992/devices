package features.auth.presentaion.mvi.event

import features.auth.data.models.User

sealed class AuthEvent {
    data class RegisterUser(val user: User) : AuthEvent()
}