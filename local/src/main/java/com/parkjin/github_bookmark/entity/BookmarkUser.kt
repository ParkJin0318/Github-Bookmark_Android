package com.parkjin.github_bookmark.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.parkjin.github_bookmark.model.User

@Entity(tableName = "bookmark_user_table")
data class BookmarkUser(
    @PrimaryKey
    val name: String,
    val profileImageUrl: String
)

fun BookmarkUser.toModel() =
    User(
        this.name,
        this.profileImageUrl
    )

fun User.toEntity() =
    BookmarkUser(
        this.name,
        this.profileImageUrl
    )

