package com.parkjin.github_bookmark.local

import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.model.BookmarkUser
import com.parkjin.github_bookmark.local.dao.BookmarkUserDao
import com.parkjin.github_bookmark.local.entity.BookmarkUserEntity
import com.parkjin.github_bookmark.local.entity.toEntity
import com.parkjin.github_bookmark.local.entity.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkUserLocalDataSourceImpl(
    private val dao: BookmarkUserDao
) : BookmarkUserDataSource {

    override fun getUsers(name: String): Flow<List<BookmarkUser>> {
        val bookmarkUserEntities =
            if (name.isBlank()) dao.getBookmarkUsers() else dao.getBookmarkUsers(name)

        return bookmarkUserEntities.map { users ->
            users.map(BookmarkUserEntity::toModel)
        }
    }

    override suspend fun activateUserBookmark(user: BookmarkUser) {
        dao.insertBookmarkUser(user.toEntity())
    }

    override suspend fun disableUserBookmark(user: BookmarkUser) {
        dao.deleteBookmarkUser(user.toEntity())
    }
}
