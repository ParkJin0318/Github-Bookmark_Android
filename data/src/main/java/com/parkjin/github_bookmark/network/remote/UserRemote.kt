package com.parkjin.github_bookmark.network.remote

import com.parkjin.github_bookmark.extension.catch
import com.parkjin.github_bookmark.network.api.UserAPI
import com.parkjin.github_bookmark.network.response.UserResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRemote @Inject constructor(
    private val api: UserAPI
) {
    fun getUsersForName(name: String): Single<List<UserResponse>> =
        api.getUsersForName(name)
            .catch()
            .map { it.items }
}
