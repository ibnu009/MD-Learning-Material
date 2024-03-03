package com.ibnu.distory.di.feature

import com.ibnu.distory.data.repository.AuthRepository
import com.ibnu.distory.data.resource.AuthDataSource
import org.koin.dsl.module

val authModule = module {
    factory { AuthRepository(get())}
    single { AuthDataSource(get(), get())}
}
