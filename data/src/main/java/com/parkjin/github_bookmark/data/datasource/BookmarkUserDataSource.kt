package com.parkjin.github_bookmark.data.datasource

import com.parkjin.github_bookmark.data.model.BookmarkUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BookmarkUserDataSource {

    fun getUsers(keyword: String): Single<List<BookmarkUser>>

    fun bookmarkUser(user: BookmarkUser): Completable

    fun unBookmarkUser(user: BookmarkUser): Completable
}
