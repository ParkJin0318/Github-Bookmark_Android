package com.parkjin.github_bookmark.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parkjin.github_bookmark.local.dao.BookmarkUserDao
import com.parkjin.github_bookmark.local.dao.GithubUserDao
import com.parkjin.github_bookmark.local.entity.BookmarkUserEntity
import com.parkjin.github_bookmark.local.entity.GithubUserEntity

@Database(
    version = 1,
    entities = [GithubUserEntity::class, BookmarkUserEntity::class],
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao
    abstract fun bookmarkUserDao(): BookmarkUserDao
}
