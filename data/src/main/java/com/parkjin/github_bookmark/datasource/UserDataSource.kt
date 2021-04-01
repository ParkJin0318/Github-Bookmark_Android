package com.parkjin.github_bookmark.datasource

import com.parkjin.github_bookmark.database.cache.UserCache
import com.parkjin.github_bookmark.mapper.toModel
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.network.remote.UserRemote
import io.reactivex.Single

/*
    remote, cache 조합하여 데이터를 반환
 */
class UserDataSource(
    private val remote: UserRemote,
    private val cache: UserCache
) {

    /*
        local 즐겨찾기 된 사용자인지 판별하여 사용자 조회
     */
    fun getAllSearchUser(name: String): Single<List<User>> =
        remote.getAllUser(name).flatMap { userDataList ->
            cache.getAllUser(name).map { userEntityList ->
                val userNameList = userEntityList.map { it.name }

                userDataList.map {
                    if (userNameList.contains(it.name))
                        it.toModel(true)
                    else
                        it.toModel(false)
                }
            }
        }

    /*
        local 즐겨찾기 된 사용자 조회
     */
    fun getAllBookmarkUser(name: String): Single<List<User>> =
        cache.getAllUser(name).map { userEntityList ->
            userEntityList.map { it.toModel() }
        }
}