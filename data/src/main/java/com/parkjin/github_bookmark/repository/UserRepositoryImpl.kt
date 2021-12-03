package com.parkjin.github_bookmark.repository

import com.parkjin.github_bookmark.datasource.UserDataSource
import com.parkjin.github_bookmark.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource
): UserRepository {

    override fun getUsersForName(name: String, page: Int): Single<List<User>> =
        dataSource.getUsersForName(name, page)

    override fun getBookmarkUsers(): Single<List<User>> =
        dataSource.getBookmarkUsers()

    override fun getBookmarkUsersForName(name: String): Single<List<User>> =
        dataSource.getBookmarkUsersForName(name)

    override fun addBookmarkUser(user: User): Completable =
        dataSource.addBookmarkUser(user)

    override fun deleteBookmarkUser(user: User): Completable =
        dataSource.deleteBookmarkUser(user)
}
