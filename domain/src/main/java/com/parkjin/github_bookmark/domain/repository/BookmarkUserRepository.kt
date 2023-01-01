package com.parkjin.github_bookmark.domain.repository

import com.parkjin.github_bookmark.domain.model.User
import kotlinx.coroutines.flow.Flow

interface BookmarkUserRepository {

    fun getUsers(name: String): Flow<List<User>>

    suspend fun activateUserBookmark(user: User)

    suspend fun disableUserBookmark(user: User)
}
