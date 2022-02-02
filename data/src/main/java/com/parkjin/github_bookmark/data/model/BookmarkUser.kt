package com.parkjin.github_bookmark.data.model

import com.parkjin.github_bookmark.domain.model.User

data class BookmarkUser(
    val name: String,
    val profileImageUrl: String
)

fun BookmarkUser.toUser() =
    User(
        name,
        profileImageUrl,
        true
    )

fun User.toBookmarkUser() =
    BookmarkUser(
        name,
        profileImageUrl
    )
