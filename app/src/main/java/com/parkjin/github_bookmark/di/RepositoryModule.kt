package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.data.repository.BookmarkUserRepositoryImpl
import com.parkjin.github_bookmark.data.repository.GithubUserRepositoryImpl
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesGithubUserRepository(
        @RemoteDataSource remoteDataSource: GithubUserDataSource,
    ): GithubUserRepository =
        GithubUserRepositoryImpl(remoteDataSource)

    @Singleton
    @Provides
    fun providesBookmarkUserRepository(
        @LocalDataSource localDataSource: BookmarkUserDataSource
    ): BookmarkUserRepository =
        BookmarkUserRepositoryImpl(localDataSource)
}
