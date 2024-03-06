package com.dicoding.asclepius

import android.app.Application
import com.dicoding.asclepius.di.feature.domainModule
import com.dicoding.asclepius.di.feature.viewModelModule
import com.dicoding.asclepius.di.localModule
import com.dicoding.asclepius.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApp)
            modules(
                listOf(
                    domainModule,
                    viewModelModule,
                    localModule,
                    networkModule,
                )
            )
        }
    }
}