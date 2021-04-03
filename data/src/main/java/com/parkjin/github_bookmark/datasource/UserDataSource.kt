package com.parkjin.github_bookmark.datasource

import com.parkjin.github_bookmark.database.cache.UserCache
import com.parkjin.github_bookmark.mapper.toEntity
import com.parkjin.github_bookmark.mapper.toModel
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.network.remote.UserRemote
import io.reactivex.Completable
import io.reactivex.Observable
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
    fun getAllSearchUser(name: String): Single<List<User>> {
        return Observable.combineLatest(
            remote.getAllUser(name).toObservable(),
            cache.getAllUser().toObservable(), { userDataList , userEntityList ->

                userDataList.map { userData ->
                    val isBookmark = userEntityList
                        .map { it.name }
                        .contains(userData.name)

                    userData.toModel(isBookmark)
                }
            }).singleOrError()
    }

    /**
     * local 즐겨찾기 된 사용자 조회
     */
    fun getAllBookmarkUser(name: String): Single<List<User>> =
        cache.getAllUser(name).map { userEntityList ->
            userEntityList.map { it.toModel() }
        }

    /**
     * local 즐겨찾기 사용자 추가
     */
    fun addBookmarkUser(user: User): Completable =
        if (!user.isBookmark)
            cache.insertUser(user.toEntity())
        else
            cache.deleteUser(user.toEntity())
}