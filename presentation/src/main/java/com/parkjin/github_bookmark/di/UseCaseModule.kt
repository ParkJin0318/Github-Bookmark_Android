package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.usecase.SelectBookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetUsersUseCase
import com.parkjin.github_bookmark.usecase.GetBookmarkUsersUseCase
import org.koin.dsl.module

/**
 * Domain 계층의 UseCase 클래스 의존성 관리 모듈
 */
val useCaseModule = module {
    single { GetUsersUseCase(get()) }
    single { GetBookmarkUsersUseCase(get()) }
    single { SelectBookmarkUserUseCase(get()) }
}