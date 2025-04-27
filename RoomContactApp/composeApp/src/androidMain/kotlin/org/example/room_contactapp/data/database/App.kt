package org.example.room_contactapp.data.database

import android.app.Application
import org.example.room_contactapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(androidContext = this@App)
        }
    }
}