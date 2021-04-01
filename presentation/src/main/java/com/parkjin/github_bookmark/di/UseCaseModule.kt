package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.usecase.GetAllSearchUserUseCase
import com.parkjin.github_bookmark.usecase.GetBookmarkUseCase
import org.koin.dsl.module

/**
 * Domain 계층의 UseCase 클래스 의존성 관리 모듈
 */
val useCaseModule = module {
    single { GetAllSearchUserUseCase(get()) }
    single { GetBookmarkUseCase(get()) }
}