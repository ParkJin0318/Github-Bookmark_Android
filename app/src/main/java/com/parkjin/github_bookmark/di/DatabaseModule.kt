package com.parkjin.github_bookmark.di

import android.app.Application
import androidx.room.Room
import com.parkjin.github_bookmark.local.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, LocalDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
}
