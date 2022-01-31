package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.GithubUserDataSourceImpl
import com.parkjin.github_bookmark.datasource.GithubUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun provideGithubUserDataSource(
        dataSourceImpl: GithubUserDataSourceImpl
    ): GithubUserDataSource
}
