package com.parkjin.github_bookmark.data.datasource

import com.parkjin.github_bookmark.domain.model.User
import io.reactivex.rxjava3.core.Single

interface GithubUserDataSource {

    fun getUsers(name: String): Single<List<User>>
}
