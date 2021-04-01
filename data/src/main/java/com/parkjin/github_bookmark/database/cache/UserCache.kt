package com.parkjin.github_bookmark.database.cache

import com.parkjin.github_bookmark.database.dao.UserDao
import com.parkjin.github_bookmark.database.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

/*
    Dao를 참조하여 DB 조회
 */
class UserCache(
    private val userDao: UserDao
) {
    fun insertUser(entity: UserEntity): Completable =
        userDao.insertUser(entity)

    fun getAllUser(): Single<List<UserEntity>> =
        userDao.getAllUser()

    fun getAllUser(name: String): Single<List<UserEntity>> =
        userDao.getAllUser(name)

    fun deleteUser(entity: UserEntity): Completable =
        userDao.deleteUser(entity)

    fun deleteAllUser(): Completable =
        userDao.deleteAllUser()
}