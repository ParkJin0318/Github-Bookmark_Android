package com.parkjin.github_bookmark.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parkjin.github_bookmark.database.dao.UserDao
import com.parkjin.github_bookmark.database.entity.UserEntity

/*
    DB의 엑세스 포인트 역할,
    DB의 버전, 테이블 등 관리
 */
@Database(
    version = 1,
    entities = [UserEntity::class],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}