package com.parkjin.github_bookmark.domain.repository

import com.parkjin.github_bookmark.domain.model.User

interface BookmarkUserRepository {

    suspend fun getUsers(name: String): List<User>

    suspend fun bookmarkUser(user: User)

    suspend fun unBookmarkUser(user: User)
}
