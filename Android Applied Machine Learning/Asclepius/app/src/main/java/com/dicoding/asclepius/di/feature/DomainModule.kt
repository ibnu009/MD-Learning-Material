package com.dicoding.asclepius.di.feature

import com.dicoding.asclepius.data.repository.ArticleRepository
import com.dicoding.asclepius.data.repository.HistoryRepository
import com.dicoding.asclepius.data.source.ArticleDataSource
import com.dicoding.asclepius.data.source.HistoryDataSource
import org.koin.dsl.module

val domainModule = module {
    factory { ArticleRepository(get()) }
    single { ArticleDataSource(get()) }
    factory { HistoryRepository(get()) }
    single { HistoryDataSource(get()) }
}
