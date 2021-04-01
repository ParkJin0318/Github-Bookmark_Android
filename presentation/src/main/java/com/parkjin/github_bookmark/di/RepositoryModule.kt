package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.repository.UserRepository
import com.parkjin.github_bookmark.repository.UserRepositoryImpl
import org.koin.dsl.module

/**
 * Domain, Data 계층의 Repository 의존성 관리 모듈
 */
val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}