package com.parkjin.github_bookmark.local

import com.parkjin.github_bookmark.local.dao.BookmarkUserDao
import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.model.BookmarkUser
import com.parkjin.github_bookmark.local.entity.toEntity
import com.parkjin.github_bookmark.local.entity.toModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class BookmarkUserLocalDataSourceImpl(
    private val dao: BookmarkUserDao
) : BookmarkUserDataSource {

    override fun getUsers(keyword: String): Single<List<BookmarkUser>> {
        val users =
            if (keyword.isEmpty() || keyword.isBlank()) dao.getBookmarkUsers()
            else dao.getBookmarkUsers(keyword)

        return users.map { list ->
            list.map { it.toModel() }
        }
    }

    override fun bookmarkUser(user: BookmarkUser): Completable =
        dao.insertBookmarkUser(user.toEntity())

    override fun unBookmarkUser(user: BookmarkUser): Completable =
        dao.deleteBookmarkUser(user.toEntity())
}
