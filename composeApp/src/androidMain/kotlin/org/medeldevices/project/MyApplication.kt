package org.medeldevices.project

import android.app.Application
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