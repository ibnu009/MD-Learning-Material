package com.ibnu.distory.di.feature

import com.ibnu.distory.presentation.add.AddStoryViewModel
import com.ibnu.distory.presentation.detail.DetailStoryViewModel
import com.ibnu.distory.presentation.home.HomeViewModel
import com.ibnu.distory.presentation.login.LoginViewModel
import com.ibnu.distory.presentation.map.StoryMapViewModel
import com.ibnu.distory.presentation.register.RegisterViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { RegisterViewModel(get()) }
    single { LoginViewModel(get()) }
    single { HomeViewModel(get()) }
    single { AddStoryViewModel(get()) }
    single { DetailStoryViewModel(get()) }
    single { StoryMapViewModel(get()) }
}