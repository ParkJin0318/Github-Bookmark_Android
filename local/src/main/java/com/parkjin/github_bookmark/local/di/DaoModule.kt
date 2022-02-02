package com.parkjin.github_bookmark.local.di

import com.parkjin.github_bookmark.local.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideUserDao(database: LocalDatabase) = database.bookmarkUserDao()

}
