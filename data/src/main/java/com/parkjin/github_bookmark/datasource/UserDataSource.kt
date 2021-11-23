package com.parkjin.github_bookmark.datasource

import com.parkjin.github_bookmark.database.cache.UserCache
import com.parkjin.github_bookmark.mapper.toEntity
import com.parkjin.github_bookmark.mapper.toModel
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.network.remote.UserRemote
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val remote: UserRemote,
    private val cache: UserCache
) {
    fun getUsersForName(name: String): Single<List<User>> =
        remote.getUsersForName(name).map { userDataList ->
            userDataList.map { it.toModel() }
        }

    fun getBookmarkUsers(): Single<List<User>> =
        cache.getUsers().map { userEntityList ->
            userEntityList.map { it.toModel() }
        }

    fun getBookmarkUsersForName(name: String): Single<List<User>> =
        cache.getUsersForName(name).map { userEntityList ->
            userEntityList.map { it.toModel() }
        }

    fun addBookmarkUser(user: User): Completable =
        cache.insertUser(user.toEntity())

    fun deleteBookmarkUser(user: User): Completable =
        cache.deleteUser(user.toEntity())
}
