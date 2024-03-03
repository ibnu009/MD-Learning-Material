package com.ibnu.gitfriend.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ibnu.gitfriend.presentation.setting.SettingPreferences
import org.koin.dsl.module

fun preferenceModule(pref: DataStore<Preferences> ) = module {
    single {
        pref
    }

    single {
        SettingPreferences.getInstance(get())
    }
}
