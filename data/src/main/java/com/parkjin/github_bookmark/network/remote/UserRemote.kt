package com.parkjin.github_bookmark.network.remote

import com.parkjin.github_bookmark.extension.catch
import com.parkjin.github_bookmark.network.api.UserAPI
import com.parkjin.github_bookmark.network.response.UserData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRemote @Inject constructor(
    private val api: UserAPI
) {
    fun getUsersForName(name: String): Single<List<UserData>> =
        api.getUsersForName(name)
            .catch()
            .map { it.items }
}
