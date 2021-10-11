package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.component.InputFieldViewModel
import com.parkjin.github_bookmark.ui.bookmark.BookmarkViewModel
import com.parkjin.github_bookmark.ui.github.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Presentation 계층의 ViewModel 클래스 의존성 관리 모듈
 */
val viewModelModule = module {
    viewModel { InputFieldViewModel() }
    viewModel { GithubViewModel(get(), get()) }
    viewModel { BookmarkViewModel(get(), get()) }
}