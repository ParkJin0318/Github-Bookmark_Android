package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.remote.api.GithubUserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): GithubUserAPI =
        retrofit.create(GithubUserAPI::class.java)
}
