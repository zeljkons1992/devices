import com.russhwolf.settings.Settings
import core.network.ApiService
import core.network.provideHttpClient
import features.auth.data.repository.AuthRepositoryImpl
import features.auth.domain.repository.AuthRepository
import features.auth.domain.usecase.RegisterUserUseCase
import features.auth.presentation.mvi.viewmodel.AuthViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule = module {
    single { AuthViewModel(get(), get()) }
}

actual val useCaseModule = module {
    factory { RegisterUserUseCase(get()) }
}

actual val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

actual val apiModule = module {
    single { provideHttpClient() }
    single { ApiService(get(),"") }
}

actual val dataStoreModule: Module
    get() = TODO("Not yet implemented")