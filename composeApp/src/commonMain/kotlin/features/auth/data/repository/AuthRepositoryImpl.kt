package features.auth.data.repository

import core.network.ApiService
import features.auth.data.models.JwtToken
import features.auth.data.models.User
import features.auth.data.models.toEntity
import features.auth.domain.entites.JwtTokenEntiy
import features.auth.domain.repository.AuthRepository
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

class AuthRepositoryImpl(private val apiClient: ApiService) : AuthRepository {

    override suspend fun userRegister(user: User): JwtTokenEntiy {
        val response: HttpResponse = apiClient.userRegister(user)
        return when (response.status.value) {
            200 -> {
                val jwtToken: JwtToken = response.body()
                jwtToken.toEntity()
            }
            400, 409 -> {
                val errorResponse: ErrorResponse = response.body()
                throw RegistrationException(errorResponse.msg)
            }
            else -> {
                throw RegistrationException("Registration failed with status code: ${response.status.value}")
            }
        }
    }

    override suspend fun userLogin(user: User): JwtTokenEntiy {
        val response: HttpResponse = apiClient.userLogin(user)
        return when(response.status.value){
            200 ->{
                val  jwtToken: JwtToken = response.body()
                jwtToken.toEntity()
            }
            else ->{
                throw  RegistrationException("NE RADI")
            }
        }
    }
}

@Serializable
data class ErrorResponse(val msg: String)

class RegistrationException(message: String) : Exception(message)