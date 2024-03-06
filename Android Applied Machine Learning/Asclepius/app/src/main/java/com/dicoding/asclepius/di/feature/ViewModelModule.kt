package com.dicoding.asclepius.di.feature

import com.dicoding.asclepius.view.history.HistoryViewModel
import com.dicoding.asclepius.view.result.ResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ResultViewModel(get(), get()) }
    viewModel { HistoryViewModel(get()) }
}