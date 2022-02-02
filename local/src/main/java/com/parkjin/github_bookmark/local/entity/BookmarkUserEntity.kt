package com.parkjin.github_bookmark.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.parkjin.github_bookmark.data.model.BookmarkUser

@Entity(tableName = "bookmark_user_table")
class BookmarkUserEntity(
    @PrimaryKey
    val name: String,
    val profileImageUrl: String
)

fun BookmarkUserEntity.toModel() =
    BookmarkUser(
        name,
        profileImageUrl
    )

fun BookmarkUser.toEntity() =
    BookmarkUserEntity(
        name,
        profileImageUrl
    )
