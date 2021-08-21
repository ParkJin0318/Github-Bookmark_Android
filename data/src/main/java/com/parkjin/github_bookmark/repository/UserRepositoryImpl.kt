package com.parkjin.github_bookmark.repository

import com.parkjin.github_bookmark.datasource.UserDataSource
import com.parkjin.github_bookmark.model.User
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Domain의 Repository 인터페이스를 구현한 클래스
 */
class UserRepositoryImpl(
    private val dataSource: UserDataSource
): UserRepository {

    override fun getUsersForName(name: String): Single<List<User>> =
        dataSource.getUsersForName(name)

    override fun getBookmarkUsers(): Single<List<User>> =
        dataSource.getBookmarkUsers()

    override fun getBookmarkUsersForName(name: String): Single<List<User>> =
        dataSource.getBookmarkUsersForName(name)

    override fun addBookmarkUser(user: User): Completable =
        dataSource.addBookmarkUser(user)

    override fun deleteBookmarkUser(user: User): Completable =
        dataSource.deleteBookmarkUser(user)
}