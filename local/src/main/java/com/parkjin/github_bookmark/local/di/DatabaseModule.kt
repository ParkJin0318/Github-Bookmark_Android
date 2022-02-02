package com.parkjin.github_bookmark.local.di

import android.app.Application
import androidx.room.Room
import com.parkjin.github_bookmark.local.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, LocalDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
}
