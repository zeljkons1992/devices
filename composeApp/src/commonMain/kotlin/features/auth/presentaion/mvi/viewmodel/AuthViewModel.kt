package features.auth.presentation.mvi.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.example.cmppreference.LocalPreference
import com.russhwolf.settings.Settings
import core.ui.ContractViewModel
import core.ui.ViewEvent
import core.ui.ViewSideEffect
import core.ui.ViewState
import core.utils.datastore.DataStoreRepository
import features.auth.data.models.JwtToken
import features.auth.domain.usecase.RegisterUserUseCase
import features.auth.data.models.User
import features.auth.domain.entites.JwtTokenEntiy
import features.auth.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val dataStoreRepository: DataStoreRepository
) : ContractViewModel<AuthState, AuthEvent, AuthSideEffect>(AuthState.Initial) {



    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    override fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.RegisterUser -> {
                viewModelScope.launch(Dispatchers.Default) {
                    try {
                        val jwtToken = registerUserUseCase.execute(event.user)
                        if (jwtToken.token.isNotEmpty()) {
                            dataStoreRepository.saveString("jwtToken", jwtToken.token)
                            setSideEffect { AuthSideEffect.NavigateToAnotherScreen }
                        } else {
                            setState { AuthState.Error("Registration failed") }
                        }
                    } catch (e: Exception) {
                        setState { AuthState.Error(e.message ?: "Unknown Error") }
                    }
                }
            }

            is AuthEvent.LoginUser -> {
                viewModelScope.launch(Dispatchers.Main) {
                    setState { AuthState.Loading }
                    try {
                        val response = loginUserUseCase.execute(event.user)
                        if (response.token.isNotEmpty()) {
                            // Sačuvaj token u DataStore
                            dataStoreRepository.saveString("jwtToken", response.token)
                            setSideEffect { AuthSideEffect.NavigateToAnotherScreen }
                        } else {
                            setState { AuthState.Error("Login Failed") }
                        }
                    } catch (e: Exception) {
                        setState { AuthState.Error(e.message ?: "Unknown error") }
                    }
                }
            }
        }
    }

    fun checkIfLoggedIn() {
        val token = dataStoreRepository.getString("jwtToken")
        _isUserLoggedIn.value = !token.isNullOrEmpty()
    }
}


sealed class AuthEvent : ViewEvent {
    data class RegisterUser(val user: User) : AuthEvent()
    data class LoginUser(val user: User): AuthEvent()
}

sealed class AuthState : ViewState {
    data object Initial: AuthState()
    data object Loading : AuthState()
    data class Error(val error: String) : AuthState()
}

sealed class AuthSideEffect : ViewSideEffect {
    data object NavigateToAnotherScreen : AuthSideEffect()
}
