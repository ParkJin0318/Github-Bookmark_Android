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
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideBookmarkUserUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        repository: BookmarkUserRepository
    ) = BookmarkUserUseCase(dispatcher, repository)

    @Singleton
    @Provides
    fun provideGetBookmarkUsersUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        repository: BookmarkUserRepository
    ) = GetBookmarkUsersUseCase(dispatcher, repository)

    @Singleton
    @Provides
    fun provideGetGithubUsersUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        githubUserRepository: GithubUserRepository,
        bookmarkUserRepository: BookmarkUserRepository
    ) = GetGithubUsersUseCase(
        dispatcher,
        githubUserRepository,
        bookmarkUserRepository
    )
}
