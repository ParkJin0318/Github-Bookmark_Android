package com.parkjin.github_bookmark.remote.di

import com.parkjin.github_bookmark.remote.GithubUserDataSourceImpl
import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
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
