package com.parkjin.github_bookmark.data.repository

import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.model.BookmarkUser
import com.parkjin.github_bookmark.data.model.toBookmarkUser
import com.parkjin.github_bookmark.data.model.toUser
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository

class BookmarkUserRepositoryImpl(
    private val localDataSource: BookmarkUserDataSource
) : BookmarkUserRepository {

    override fun getUsers(name: String): List<User> =
        localDataSource.getUsers(name)
            .map(BookmarkUser::toUser)

    override fun bookmarkUser(user: User) =
        localDataSource.bookmarkUser(user.toBookmarkUser())

    override fun unBookmarkUser(user: User) =
        localDataSource.unBookmarkUser(user.toBookmarkUser())
}
