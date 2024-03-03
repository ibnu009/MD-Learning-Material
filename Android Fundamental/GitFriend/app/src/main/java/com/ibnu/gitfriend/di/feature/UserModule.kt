package com.ibnu.gitfriend.di.feature

import com.ibnu.gitfriend.data.repository.UserRepository
import com.ibnu.gitfriend.data.source.UserDataSource
import org.koin.dsl.module

val userModule = module {

    factory { UserRepository(get()) }

    single { UserDataSource(get(), get()) }
}
