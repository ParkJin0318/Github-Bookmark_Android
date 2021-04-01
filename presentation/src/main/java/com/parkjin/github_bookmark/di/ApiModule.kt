package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.network.api.UserAPI
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Data 계층의 API 의존성 관리 모듈
 */
val apiModule = module {
    single { (get() as Retrofit).create(UserAPI::class.java) }
}