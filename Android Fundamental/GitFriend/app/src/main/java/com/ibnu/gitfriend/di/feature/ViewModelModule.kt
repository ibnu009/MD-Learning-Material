package com.ibnu.gitfriend.di.feature

import com.ibnu.gitfriend.presentation.MainViewModel
import com.ibnu.gitfriend.presentation.detail.DetailUserViewModel
import com.ibnu.gitfriend.presentation.favorite.FavoriteViewModel
import com.ibnu.gitfriend.presentation.follower.FollowerViewModel
import com.ibnu.gitfriend.presentation.following.FollowingViewModel
import com.ibnu.gitfriend.presentation.home.HomeViewModel
import com.ibnu.gitfriend.presentation.search.SearchViewModel
import com.ibnu.gitfriend.presentation.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailUserViewModel(get()) }
    viewModel { FollowerViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}