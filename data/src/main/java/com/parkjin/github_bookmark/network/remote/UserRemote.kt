package com.parkjin.github_bookmark.network.remote

import com.parkjin.github_bookmark.network.api.UserAPI
import com.parkjin.github_bookmark.network.response.UserData
import com.parkjin.github_bookmark.util.Constants
import io.reactivex.Single

/**
 * API를 호출하여 Response 데이터를 받는 클래스
 */
class UserRemote(
    private val api: UserAPI
) {
    fun getAllUser(name: String): Single<List<UserData>> =
        api.getAllUser(name, Constants.PAGE, Constants.PER_PAGE).map { it.body()?.items }
}