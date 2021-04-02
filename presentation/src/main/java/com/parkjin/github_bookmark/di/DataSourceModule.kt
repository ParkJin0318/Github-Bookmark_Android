package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.datasource.UserDataSource
import org.koin.dsl.module

/**
 * Data 계층의 DataSource 의존성 관리 모듈
 */
val dataSourceModule = module {
    single { UserDataSource(get(), get()) }
}