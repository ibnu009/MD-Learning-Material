package com.ibnu.distory.di.feature

import com.ibnu.distory.data.repository.StoryRepository
import com.ibnu.distory.data.resource.StoryDataSource
import org.koin.dsl.module

val storyModule = module {

    factory { StoryRepository(get()) }
    single { StoryDataSource(get(), get()) }
}
