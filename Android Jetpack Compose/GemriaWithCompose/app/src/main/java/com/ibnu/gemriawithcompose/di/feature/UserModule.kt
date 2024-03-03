package com.ibnu.gemriawithcompose.di.feature

import com.ibnu.gemriawithcompose.data.repository.GameRepository
import org.koin.dsl.module

val gameModule = module {
    factory { GameRepository(get()) }
}
