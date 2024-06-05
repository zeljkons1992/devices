package org.medeldevices.project

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.cmppreference.AppContext
import com.example.cmppreference.LocalPreference
import com.russhwolf.settings.Settings
import getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(getSharedModules())
        }

    }
}