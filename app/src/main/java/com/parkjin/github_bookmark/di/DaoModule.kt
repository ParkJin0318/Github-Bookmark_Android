package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.local.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideBookmarkUserDao(database: LocalDatabase) = database.bookmarkUserDao()
}
