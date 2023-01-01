package com.parkjin.github_bookmark.data.repository

import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.model.BookmarkUser
import com.parkjin.github_bookmark.data.model.toBookmarkUser
import com.parkjin.github_bookmark.data.model.toUser
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkUserRepositoryImpl(
    private val localDataSource: BookmarkUserDataSource
) : BookmarkUserRepository {

    override fun getUsers(name: String): Flow<List<User>> =
        localDataSource.getUsers(name)
            .map { bookmarkUsers ->
                bookmarkUsers.map(BookmarkUser::toUser)
            }

    override suspend fun activateUserBookmark(user: User) {
        localDataSource.activateUserBookmark(user.toBookmarkUser())
    }

    override suspend fun disableUserBookmark(user: User) {
        localDataSource.disableUserBookmark(user.toBookmarkUser())
    }
}
