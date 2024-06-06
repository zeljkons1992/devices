import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import core.network.ApiService
import core.network.provideHttpClient
import core.utils.datastore.BaseDataStoreRepository
import core.utils.datastore.DataStoreRepository
import core.utils.datastore.DataStoreViewModel
import core.utils.imageConverter.ImageStorage
import features.auth.data.repository.AuthRepositoryImpl
import features.auth.domain.repository.AuthRepository
import features.auth.domain.usecase.LoginUserUseCase
import features.auth.domain.usecase.RegisterUserUseCase
import features.auth.presentation.mvi.viewmodel.AuthViewModel
import features.home.data.repository.DeviceRepositoryImpl
import features.home.domain.repository.DeviceRepository
import features.home.domain.usecase.AddDeviceUseCase
import features.home.domain.usecase.GetRemoteDevicesUseCase
import features.home.presentation.viewmodel.HomeViewModel
import features.home.presentation.viewmodel.RemoteDevicesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { RemoteDevicesViewModel(get()) }
}

actual val useCaseModule = module {
    factory { RegisterUserUseCase(get()) }
    factory { LoginUserUseCase(get()) }
    factory { AddDeviceUseCase(get()) }
    factory { GetRemoteDevicesUseCase(get()) }
}

actual val repositoryModule = module {
    single<ImageStorage> {ImageStorage(androidContext())}
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<DeviceRepository> { DeviceRepositoryImpl(get(), get()) }
}

actual val apiModule = module {

    single { provideHttpClient(get()) }
    single { ApiService(get(), "https://7f79-82-117-207-248.ngrok-free.app") }
}
actual val dataStoreModule = module {
    single<Settings> {
        val sharedPreferences =
            androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        AndroidSettings(sharedPreferences)
    }
    single<DataStoreRepository> { BaseDataStoreRepository(get()) }
    viewModel { DataStoreViewModel(get()) }
}
