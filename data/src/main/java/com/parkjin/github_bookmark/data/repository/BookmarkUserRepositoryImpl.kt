package com.parkjin.github_bookmark.data.repository

import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.model.toBookmarkUser
import com.parkjin.github_bookmark.data.model.toUser
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class BookmarkUserRepositoryImpl(
    private val localDataSource: BookmarkUserDataSource
) : BookmarkUserRepository {

    override fun getUsers(keyword: String): Single<List<User>> =
        localDataSource.getUsers(keyword)
            .map { list ->
                list.map { it.toUser() }
            }

    override fun bookmarkUser(user: User): Completable =
        localDataSource.bookmarkUser(user.toBookmarkUser())

    override fun unBookmarkUser(user: User): Completable =
        localDataSource.unBookmarkUser(user.toBookmarkUser())
}
