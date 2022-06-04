package com.parkjin.github_bookmark.data.datasource

import com.parkjin.github_bookmark.data.model.BookmarkUser

interface BookmarkUserDataSource {

    suspend fun getUsers(name: String): List<BookmarkUser>

    suspend fun bookmarkUser(user: BookmarkUser)

    suspend fun unBookmarkUser(user: BookmarkUser)
}
