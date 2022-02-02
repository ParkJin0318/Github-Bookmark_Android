package com.parkjin.github_bookmark.domain.repository

import com.parkjin.github_bookmark.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BookmarkUserRepository {

    fun getUsers(keyword: String): Single<List<User>>

    fun bookmarkUser(user: User): Completable

    fun unBookmarkUser(user: User): Completable
}
