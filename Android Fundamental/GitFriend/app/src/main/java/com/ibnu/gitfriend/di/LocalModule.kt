package com.ibnu.gitfriend.di

import android.app.Application
import androidx.room.Room
import com.ibnu.gitfriend.BuildConfig
import com.ibnu.gitfriend.data.local.room.GitFriendDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { provideAuthService(get()) }

    factory { get<GitFriendDatabase>().getUserDao() }

    fun provideDatabase(application: Application): GitFriendDatabase {
        return Room.databaseBuilder(application, GitFriendDatabase::class.java, BuildConfig.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideDatabase(androidApplication()) }
}