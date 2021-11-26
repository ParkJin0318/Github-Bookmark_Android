package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.provider.SchedulerProvider
import com.parkjin.github_bookmark.repository.UserRepository
import com.parkjin.github_bookmark.usecase.GetUsersUseCase
import com.parkjin.github_bookmark.usecase.BookmarkUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetUsersUseCase(
        scheduler: SchedulerProvider,
        repository: UserRepository
    ): GetUsersUseCase = GetUsersUseCase(scheduler, repository)

    @Provides
    fun provideBookmarkUserUseCase(
        scheduler: SchedulerProvider,
        repository: UserRepository
    ): BookmarkUserUseCase = BookmarkUserUseCase(scheduler, repository)
}
