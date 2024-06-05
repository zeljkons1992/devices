package features.auth.domain.entites

data class UserEntity(
    val username: String,
    val email :String,
    val password: String
)

data class UserLoginEntity(
    val email: String,
    val password: String
)