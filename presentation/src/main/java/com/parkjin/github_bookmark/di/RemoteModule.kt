package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.network.remote.UserRemote
import org.koin.dsl.module

/**
 * Data 계층의 Remote 의존성 관리 모듈
 */
val remoteModule = module {
    single { UserRemote(get()) }
}