package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.local.BookmarkUserLocalDataSourceImpl
import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.local.GithubUserLocalDataSourceImpl
import com.parkjin.github_bookmark.local.dao.BookmarkUserDao
import com.parkjin.github_bookmark.local.dao.GithubUserDao
import com.parkjin.github_bookmark.remote.GithubUserRemoteDataSourceImpl
import com.parkjin.github_bookmark.remote.api.GithubUserAPI
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @LocalDataSource
    @Provides
    fun providesBookmarkUserLocalDataSource(
        dao: BookmarkUserDao
    ): BookmarkUserDataSource = BookmarkUserLocalDataSourceImpl(dao)

    @LocalDataSource
    @Provides
    fun providesGithubUserLocalDataSource(
        dao: GithubUserDao
    ): GithubUserDataSource = GithubUserLocalDataSourceImpl(dao)

    @RemoteDataSource
    @Provides
    fun providesGithubUserRemoteDataSource(
        api: GithubUserAPI
    ): GithubUserDataSource = GithubUserRemoteDataSourceImpl(api)
}
