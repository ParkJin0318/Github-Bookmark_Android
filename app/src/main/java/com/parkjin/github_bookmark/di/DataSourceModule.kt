package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.local.BookmarkUserLocalDataSourceImpl
import com.parkjin.github_bookmark.local.dao.BookmarkUserDao
import com.parkjin.github_bookmark.remote.GithubUserRemoteDataSourceImpl
import com.parkjin.github_bookmark.remote.api.GithubUserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @LocalDataSource
    @Singleton
    @Provides
    fun providesBookmarkUserLocalDataSource(
        dao: BookmarkUserDao
    ): BookmarkUserDataSource = BookmarkUserLocalDataSourceImpl(dao)

    @RemoteDataSource
    @Singleton
    @Provides
    fun providesGithubUserRemoteDataSource(
        api: GithubUserAPI
    ): GithubUserDataSource = GithubUserRemoteDataSourceImpl(api)
}
