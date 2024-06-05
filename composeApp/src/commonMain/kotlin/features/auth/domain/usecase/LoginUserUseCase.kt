package features.auth.domain.usecase

import features.auth.data.models.User
import features.auth.domain.entites.JwtTokenEntiy
import features.auth.domain.repository.AuthRepository

class LoginUserUseCase(private val repository: AuthRepository) {
    suspend fun execute(user: User):JwtTokenEntiy{
        return repository.userLogin(user)
    }
}