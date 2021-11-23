package com.parkjin.github_bookmark.di

import android.app.Application
import androidx.room.Room
import com.parkjin.github_bookmark.database.AppDatabase
import com.parkjin.github_bookmark.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

}
