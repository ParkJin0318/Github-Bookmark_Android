package com.parkjin.github_bookmark

import com.parkjin.github_bookmark.dao.BookmarkUserDao
import com.parkjin.github_bookmark.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.entity.toEntity
import com.parkjin.github_bookmark.entity.toModel
import com.parkjin.github_bookmark.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BookmarkUserDataSourceImpl @Inject constructor(
    private val dao: BookmarkUserDao
) : BookmarkUserDataSource {

    override fun getUsers(name: String): Single<List<User>> {
        val users =
            if (name.isEmpty() || name.isBlank()) dao.getBookmarkUsers()
            else dao.getBookmarkUsers(name)

        return users.map { list ->
            list.map { it.toModel() }
        }
    }

    override fun bookmarkUser(user: User): Completable =
        dao.insertBookmarkUser(user.toEntity())

    override fun unBookmarkUser(user: User): Completable =
        dao.deleteBookmarkUser(user.toEntity())
}
