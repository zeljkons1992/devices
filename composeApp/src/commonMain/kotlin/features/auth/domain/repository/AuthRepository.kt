package features.auth.domain.repository

import features.auth.data.models.JwtToken
import features.auth.data.models.User
import features.auth.domain.entites.JwtTokenEntiy

interface AuthRepository {
    suspend fun userLogin(user: User): JwtTokenEntiy
}