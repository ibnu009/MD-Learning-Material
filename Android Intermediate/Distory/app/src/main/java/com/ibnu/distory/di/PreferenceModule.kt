package com.ibnu.distory.di

import com.ibnu.distory.utils.PreferenceManager
import org.koin.dsl.module

val preferenceModule = module {

    single { PreferenceManager(get()) }

}