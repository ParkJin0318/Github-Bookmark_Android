package com.parkjin.github_bookmark.data.datasource

import com.parkjin.github_bookmark.data.model.BookmarkUser
import kotlinx.coroutines.flow.Flow

interface BookmarkUserDataSource {

    fun getUsers(name: String): Flow<List<BookmarkUser>>

    suspend fun activateUserBookmark(user: BookmarkUser)

    suspend fun disableUserBookmark(user: BookmarkUser)
}
