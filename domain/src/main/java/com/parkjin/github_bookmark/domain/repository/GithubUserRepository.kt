package com.parkjin.github_bookmark.domain.repository

import com.parkjin.github_bookmark.domain.model.User
import io.reactivex.rxjava3.core.Single

interface GithubUserRepository {

    fun getUsers(keyword: String): Single<List<User>>
}
