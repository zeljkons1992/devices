package org.medeldevices.project

import android.app.Application
import core.di.initKoin


class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}