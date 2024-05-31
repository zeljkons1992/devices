package features.auth.data.models

import features.auth.domain.entites.JwtTokenEntiy
import features.auth.domain.entites.UserEntity
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val email: String,
    val password: String
)

@Serializable
data class JwtToken(
    val token: String
)

fun JwtToken.toEntity(): JwtTokenEntiy{
    return JwtTokenEntiy(
        token = token
    )
}