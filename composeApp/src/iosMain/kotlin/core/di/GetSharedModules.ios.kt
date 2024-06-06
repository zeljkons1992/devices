import core.network.ApiService
import core.network.provideHttpClient
import features.auth.data.repository.AuthRepositoryImpl
import features.auth.domain.repository.AuthRepository
import features.auth.domain.usecase.RegisterUserUseCase
import features.auth.presentation.mvi.viewmodel.AuthViewModel
import features.home.data.repository.DeviceRepositoryImpl
import features.home.domain.repository.DeviceRepository
import features.home.domain.usecase.AddDeviceUseCase
import org.koin.dsl.module

actual val viewModelModule = module {
    single { AuthViewModel(get(), get(), get()) }
    single { HomeViewModel(get()) }
}

actual val useCaseModule = module {
    factory { RegisterUserUseCase(get()) }
    factory { AddDeviceUseCase(get()) }
}

actual val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<DeviceRepository> { DeviceRepositoryImpl(get()) }
}

actual val apiModule = module {
    single { provideHttpClient() }
    single { ApiService(get(), "") }
}

actual val dataStoreModule = module {

}