package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetBookmarkUsersUseCase
import com.parkjin.github_bookmark.domain.usecase.GetGithubUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideBookmarkUserUseCase(
        repository: BookmarkUserRepository
    ) = BookmarkUserUseCase(repository)

    @Singleton
    @Provides
    fun provideGetBookmarkUsersUseCase(
        repository: BookmarkUserRepository
    ) = GetBookmarkUsersUseCase(repository)

    @Singleton
    @Provides
    fun provideGetGithubUsersUseCase(
        githubUserRepository: GithubUserRepository,
        bookmarkUserRepository: BookmarkUserRepository
    ) = GetGithubUsersUseCase(
        githubUserRepository,
        bookmarkUserRepository
    )
}
