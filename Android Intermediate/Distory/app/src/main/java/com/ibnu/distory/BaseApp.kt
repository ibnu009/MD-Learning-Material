package com.ibnu.distory

import android.app.Application
import com.ibnu.distory.di.feature.authModule
import com.ibnu.distory.di.feature.storyModule
import com.ibnu.distory.di.feature.viewModelModule
import com.ibnu.distory.di.localModule
import com.ibnu.distory.di.networkModule
import com.ibnu.distory.di.preferenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApp)
            modules(
                listOf(
                    networkModule,
                    localModule,
                    preferenceModule,
                    authModule,
                    storyModule,
                    viewModelModule
                )
            )
        }
    }
}