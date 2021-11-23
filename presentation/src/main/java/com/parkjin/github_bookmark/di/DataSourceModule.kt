package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.database.cache.UserCache
import com.parkjin.github_bookmark.datasource.UserDataSource
import com.parkjin.github_bookmark.network.remote.UserRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideUserDataSource(remote: UserRemote, cache: UserCache): UserDataSource =
        UserDataSource(remote, cache)

}
