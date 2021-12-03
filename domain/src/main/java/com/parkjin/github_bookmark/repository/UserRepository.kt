package com.parkjin.github_bookmark.repository

import com.parkjin.github_bookmark.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun getUsersForName(name: String, page: Int): Single<List<User>>

    fun getBookmarkUsers(): Single<List<User>>

    fun getBookmarkUsersForName(name: String): Single<List<User>>

    fun addBookmarkUser(user: User): Completable

    fun deleteBookmarkUser(user: User): Completable
}
