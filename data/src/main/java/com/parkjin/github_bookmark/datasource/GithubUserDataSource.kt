package com.parkjin.github_bookmark.datasource

import com.parkjin.github_bookmark.model.User
import io.reactivex.rxjava3.core.Single

interface GithubUserDataSource {

    fun getUsers(name: String): Single<List<User>>
}
