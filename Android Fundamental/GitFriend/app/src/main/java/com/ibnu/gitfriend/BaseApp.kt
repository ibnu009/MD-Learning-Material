package com.ibnu.gitfriend

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.ibnu.gitfriend.di.feature.userModule
import com.ibnu.gitfriend.di.feature.viewModelModule
import com.ibnu.gitfriend.di.localModule
import com.ibnu.gitfriend.di.networkModule
import com.ibnu.gitfriend.di.preferenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

private val Context.dataStore by preferencesDataStore(name = "settings")
class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApp)
            modules(
                listOf(
                    networkModule,
                    userModule,
                    localModule,
                    preferenceModule(dataStore),
                    viewModelModule
                )
            )
        }
    }
}