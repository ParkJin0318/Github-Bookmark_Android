package com.parkjin.github_bookmark.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parkjin.github_bookmark.dao.BookmarkUserDao
import com.parkjin.github_bookmark.entity.BookmarkUser

@Database(
    version = 1,
    entities = [BookmarkUser::class],
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun bookmarkUserDao(): BookmarkUserDao
}
