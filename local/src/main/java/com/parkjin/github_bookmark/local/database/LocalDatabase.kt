package com.parkjin.github_bookmark.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parkjin.github_bookmark.local.dao.BookmarkUserDao
import com.parkjin.github_bookmark.local.entity.BookmarkUserEntity

@Database(
    version = 1,
    entities = [BookmarkUserEntity::class],
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun bookmarkUserDao(): BookmarkUserDao
}
