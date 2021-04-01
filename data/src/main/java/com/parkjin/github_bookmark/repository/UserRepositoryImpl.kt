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

    override fun getAllSearchUser(name: String): Single<List<User>> =
        dataSource.getAllSearchUser(name)

    override fun getAllBookmarkUser(name: String): Single<List<User>> =
        dataSource.getAllBookmarkUser(name)

    override fun addBookmarkUser(user: User): Completable =
        dataSource.addBookmarkUser(user)
}