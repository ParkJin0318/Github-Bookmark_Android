package com.parkjin.github_bookmark.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    Room DB의 Table,
    즐겨찾기 된 사용자 정보를 저장
 */
@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    val name: String,
    val profileImageUrl: String
)