package com.ibnu.gemriawithcompose.di.feature

import com.ibnu.gemriawithcompose.presentation.cart.CartViewModel
import com.ibnu.gemriawithcompose.presentation.detail.DetailViewModel
import com.ibnu.gemriawithcompose.presentation.home.HomeViewModel
import com.ibnu.gemriawithcompose.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { CartViewModel(get()) }
}