package com.parkjin.github_bookmark.database.cache

import com.parkjin.github_bookmark.database.dao.UserDao
import com.parkjin.github_bookmark.database.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserCache @Inject constructor(
    private val userDao: UserDao
) {
    fun insertUser(entity: UserEntity): Completable =
        userDao.insertUser(entity)

    fun getUsers(): Single<List<UserEntity>> =
        userDao.getUsers()

    fun getUsersForName(name: String): Single<List<UserEntity>> =
        userDao.getUsersForName(name)

    fun deleteUser(entity: UserEntity): Completable =
        userDao.deleteUser(entity)
}
