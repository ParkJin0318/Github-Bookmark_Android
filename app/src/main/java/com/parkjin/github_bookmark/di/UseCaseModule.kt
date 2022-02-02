package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideBookmarkUserUseCase(
        repository: BookmarkUserRepository
    ) = BookmarkUserUseCase(repository)

    @Provides
    fun provideGetUserUseCase(
        githubUserRepository: GithubUserRepository,
        bookmarkUserRepository: BookmarkUserRepository
    ) = GetUsersUseCase(
        githubUserRepository,
        bookmarkUserRepository
    )
}
