package com.parkjin.github_bookmark.di

import android.app.Application
import androidx.room.Room
import com.parkjin.github_bookmark.database.AppDatabase
import com.parkjin.github_bookmark.database.dao.UserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Data 계층의 Room Dao 의존성 관리 모듈
 */
val daoModule = module {
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    single { provideUserDao(get()) }
}

/**
 * Data 계층의 Room Database 모듈
 */
val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { provideDatabase(androidApplication()) }
}