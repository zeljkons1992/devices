package features.auth.presentaion.mvi.state

import features.auth.data.models.JwtToken
import features.auth.domain.entites.JwtTokenEntiy

sealed class AuthState {
    data class SuccessRegistration(val jwtToken:JwtTokenEntiy):AuthState()
    data class ErroRegistration(val message:String):AuthState()
}