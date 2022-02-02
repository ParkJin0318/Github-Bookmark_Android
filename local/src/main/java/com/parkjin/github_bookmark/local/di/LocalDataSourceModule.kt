package com.parkjin.github_bookmark.local.di

import com.parkjin.github_bookmark.local.BookmarkUserDataSourceImpl
import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideBookmarkUserDataSource(
        dataSourceImpl: BookmarkUserDataSourceImpl
    ): BookmarkUserDataSource
}
