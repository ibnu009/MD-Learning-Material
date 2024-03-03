package com.ibnu.distory.di

import android.app.Application
import androidx.room.Room
import com.ibnu.distory.BuildConfig
import com.ibnu.distory.data.local.room.DistoryDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { provideAuthService(get()) }

    factory { get<DistoryDatabase>().getStoryDao() }

    fun provideDatabase(application: Application): DistoryDatabase {
        return Room.databaseBuilder(application, DistoryDatabase::class.java, BuildConfig.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideDatabase(androidApplication()) }
}