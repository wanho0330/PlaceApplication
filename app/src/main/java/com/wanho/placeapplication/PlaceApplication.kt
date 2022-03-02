package com.wanho.placeapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PlaceApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@PlaceApplication)
            modules(listOf(viewModelModule, repositoryModule, useCaseModule, networkModule, databaseModule))
        }
    }
}