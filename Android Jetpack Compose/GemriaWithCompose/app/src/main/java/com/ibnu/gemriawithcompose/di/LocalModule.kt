package com.ibnu.gemriawithcompose.di

import android.app.Application
import androidx.room.Room
import com.ibnu.gemriawithcompose.BuildConfig
import com.ibnu.gemriawithcompose.data.local.room.GameriaDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val localModule = module {
    factory { get<GameriaDatabase>().getGameDao() }

    fun provideDatabase(application: Application): GameriaDatabase {
        return Room.databaseBuilder(application, GameriaDatabase::class.java, BuildConfig.DB_NAME)
            .fallbackToDestructiveMigration()
           .build()
    }

    single { provideDatabase(androidApplication()) }
}