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
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module



actual val viewModelModule = module {
    viewModel { AuthViewModel(get(), get(), get() ) }
}

actual val useCaseModule = module {
    factory { RegisterUserUseCase(get()) }
    factory { LoginUserUseCase(get()) }
}

actual val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

actual val apiModule = module {
    single { provideHttpClient() }
    single { ApiService(get(),"https://edc5-82-117-207-248.ngrok-free.app") }
}
actual val dataStoreModule = module {
    single<Settings> {
        val sharedPreferences = androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        AndroidSettings(sharedPreferences)
    }
    single<DataStoreRepository> { BaseDataStoreRepository(get()) }
    viewModel { DataStoreViewModel(get()) }
}
