package features.auth.data.repository

import core.network.ApiService
import features.auth.data.models.JwtToken
import features.auth.data.models.User
import features.auth.data.models.toEntity
import features.auth.domain.entites.JwtTokenEntiy
import features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(private val apiClient: ApiService): AuthRepository {



    override suspend fun userRegister(user: User): JwtTokenEntiy {
        val response: JwtToken = apiClient.userLogin(user)
        return  response.toEntity();
    }
}