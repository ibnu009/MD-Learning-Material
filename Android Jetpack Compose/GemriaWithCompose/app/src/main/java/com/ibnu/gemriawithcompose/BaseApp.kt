package com.ibnu.gemriawithcompose

import android.app.Application
import com.ibnu.gemriawithcompose.di.feature.gameModule
import com.ibnu.gemriawithcompose.di.feature.viewModelModule
import com.ibnu.gemriawithcompose.di.localModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApp)
            modules(
                listOf(
                    gameModule,
                    localModule,
                    viewModelModule
                )
            )
        }
    }
}