import android.content.Context
import android.content.SharedPreferences
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import core.network.ApiService
import core.network.provideHttpClient
import core.utils.datastore.BaseDataStoreRepository
import core.utils.datastore.DataStoreRepository
import core.utils.datastore.DataStoreViewModel
import features.auth.data.repository.AuthRepositoryImpl
import features.auth.domain.repository.AuthRepository
import features.auth.domain.usecase.LoginUserUseCase
import features.auth.domain.usecase.RegisterUserUseCase
import features.auth.presentation.mvi.viewmodel.AuthViewModel
import features.home.data.repository.DeviceRepositoryImpl
import features.home.domain.repository.DeviceRepository
import features.home.domain.usecase.AddDeviceUseCase
import features.home.presentation.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.compose.koinInject
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModel { AuthViewModel(get(), get(), get() ) }
    viewModel { HomeViewModel(get()) }
}

actual val useCaseModule = module {
    factory { RegisterUserUseCase(get()) }
    factory { LoginUserUseCase(get()) }
    factory { AddDeviceUseCase(get()) }
}

actual val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<DeviceRepository> { DeviceRepositoryImpl(get()) }
}

actual val apiModule = module {
    single { provideHttpClient() }
    single { ApiService(get(),"https://8edc-82-117-207-248.ngrok-free.app","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNjY1ZGMwYmM2NWRlMjQ2ZDY1MGJhMTFkIiwiZW1haWwiOiJ0ZXN0MTIzNEBnbWFpbC5jb20iLCJ1c2VybmFtZSI6InRlc3QxMjM0In0sImlhdCI6MTcxNzUwNDg3OSwiZXhwIjoxNzE3OTM2ODc5fQ.Y0WlYYtMkvLax5h4n7Lvge9m9mNOVT674bzhW-VqEgc") }
}
actual val dataStoreModule = module {
    single<Settings> {
        val sharedPreferences = androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        AndroidSettings(sharedPreferences)
    }
    single<DataStoreRepository> { BaseDataStoreRepository(get()) }
    viewModel { DataStoreViewModel(get()) }
}
