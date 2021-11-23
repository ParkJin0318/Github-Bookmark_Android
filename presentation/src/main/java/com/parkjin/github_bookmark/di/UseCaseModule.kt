package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.repository.UserRepository
import com.parkjin.github_bookmark.usecase.GetBookmarkUsersUseCase
import com.parkjin.github_bookmark.usecase.GetUsersUseCase
import com.parkjin.github_bookmark.usecase.SelectBookmarkUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetUsersUseCase(repository: UserRepository): GetUsersUseCase =
        GetUsersUseCase(repository)

    @Provides
    fun provideGetBookmarkUsersUseCase(repository: UserRepository): GetBookmarkUsersUseCase =
        GetBookmarkUsersUseCase(repository)

    @Provides
    fun provideSelectBookmarkUserUseCase(repository: UserRepository): SelectBookmarkUserUseCase =
        SelectBookmarkUserUseCase(repository)
}
