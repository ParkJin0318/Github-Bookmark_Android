package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.database.cache.UserCache
import com.parkjin.github_bookmark.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    fun provideUserCache(dao: UserDao): UserCache = UserCache(dao)

}
