package com.parkjin.github_bookmark.local

import com.parkjin.github_bookmark.local.dao.BookmarkUserDao
import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.model.BookmarkUser
import com.parkjin.github_bookmark.local.entity.BookmarkUserEntity
import com.parkjin.github_bookmark.local.entity.toEntity
import com.parkjin.github_bookmark.local.entity.toModel

class BookmarkUserLocalDataSourceImpl(
    private val dao: BookmarkUserDao
) : BookmarkUserDataSource {

    override fun getUsers(name: String): List<BookmarkUser> {
        val users =
            if (name.isEmpty() || name.isBlank()) dao.getBookmarkUsers()
            else dao.getBookmarkUsers(name)

        return users.map(BookmarkUserEntity::toModel)
    }

    override fun bookmarkUser(user: BookmarkUser) =
        dao.insertBookmarkUser(user.toEntity())

    override fun unBookmarkUser(user: BookmarkUser) =
        dao.deleteBookmarkUser(user.toEntity())
}
