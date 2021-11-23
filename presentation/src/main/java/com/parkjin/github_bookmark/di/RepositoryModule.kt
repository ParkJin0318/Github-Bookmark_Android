package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.repository.UserRepository
import com.parkjin.github_bookmark.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository
}
