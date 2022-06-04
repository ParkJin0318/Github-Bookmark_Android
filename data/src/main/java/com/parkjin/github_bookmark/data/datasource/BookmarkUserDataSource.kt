package com.parkjin.github_bookmark.data.datasource

import com.parkjin.github_bookmark.data.model.BookmarkUser

interface BookmarkUserDataSource {

    fun getUsers(name: String): List<BookmarkUser>

    fun bookmarkUser(user: BookmarkUser)

    fun unBookmarkUser(user: BookmarkUser)
}
