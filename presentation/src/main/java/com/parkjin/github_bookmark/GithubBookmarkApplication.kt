package com.parkjin.github_bookmark

import android.app.Application
import com.parkjin.github_bookmark.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * 앱 실행시, Koin 모듈 작동
 */
class GithubBookmarkApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GithubBookmarkApplication)
            val modules = listOf(
                databaseModule, networkModule, apiModule, daoModule, cacheModule, remoteModule,
                dataSourceModule, repositoryModule, useCaseModule, viewModelModule
            )
            modules(modules)
        }
    }
}