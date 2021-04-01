package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.database.cache.UserCache
import org.koin.dsl.module

/**
 * Data 계층의 Cache 의존성 관리 모듈
 */
val cacheModule = module {
    single { UserCache(get()) }
}