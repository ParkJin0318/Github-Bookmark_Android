package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.remote.api.GithubUserApi
import com.parkjin.github_bookmark.remote.api.GithubUserApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideUserApi(httpClient: HttpClient): GithubUserApi =
        GithubUserApiImpl(httpClient)
}
