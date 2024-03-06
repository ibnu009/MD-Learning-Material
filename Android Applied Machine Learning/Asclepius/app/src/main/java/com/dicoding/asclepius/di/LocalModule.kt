package com.dicoding.asclepius.di

import android.app.Application
import androidx.room.Room
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.local.room.AsclepiusDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { provideArticleService(get()) }

    factory { get<AsclepiusDatabase>().getHistoryDao() }

    fun provideDatabase(application: Application): AsclepiusDatabase {
        return Room.databaseBuilder(application, AsclepiusDatabase::class.java,
            BuildConfig.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideDatabase(androidApplication()) }
}