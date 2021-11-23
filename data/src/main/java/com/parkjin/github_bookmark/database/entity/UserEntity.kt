package com.parkjin.github_bookmark.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    val name: String,
    val profileImageUrl: String
)
