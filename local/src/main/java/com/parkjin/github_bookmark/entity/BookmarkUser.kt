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
        name = name,
        profileImageUrl = profileImageUrl,
        bookmarked = true
    )

fun User.toEntity() =
    BookmarkUser(
        name = name,
        profileImageUrl = profileImageUrl
    )
