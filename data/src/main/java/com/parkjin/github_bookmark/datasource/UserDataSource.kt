package com.parkjin.github_bookmark.datasource

import com.parkjin.github_bookmark.database.cache.UserCache
import com.parkjin.github_bookmark.mapper.toEntity
import com.parkjin.github_bookmark.mapper.toModel
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.network.remote.UserRemote
import io.reactivex.Completable
import io.reactivex.Single

/**
 * remote, cache 조합하여 데이터를 반환
 */
class UserDataSource(
    private val remote: UserRemote,
    private val cache: UserCache
) {
    /**
     * API 사용자 조회 및 local의 즐겨찾기 된 사용자인지 판별
     */
    fun getUsersForName(name: String): Single<List<User>> =
        remote.getUsersForName(name).map { userDataList ->
            userDataList.map { it.toModel() }
        }

    /**
     * local 즐겨찾기 사용자 중 이름에 해당하는 사용자 조회
     */
    fun getBookmarkUsers(): Single<List<User>> =
        cache.getUsers().map { userEntityList ->
            userEntityList.map { it.toModel() }
        }

    /**
     * local 즐겨찾기 사용자 중 이름에 해당하는 사용자 조회
     */
    fun getBookmarkUsersForName(name: String): Single<List<User>> =
        cache.getUsersForName(name).map { userEntityList ->
            userEntityList.map { it.toModel() }
        }

    /**
     * local 즐겨찾기 사용자 추가
     */
    fun addBookmarkUser(user: User): Completable =
        cache.insertUser(user.toEntity())

    /**
     * local 즐겨찾기 사용자 삭제
     */
    fun deleteBookmarkUser(user: User): Completable =
        cache.deleteUser(user.toEntity())
}