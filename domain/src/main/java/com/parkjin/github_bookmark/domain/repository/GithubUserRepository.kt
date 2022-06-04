package com.parkjin.github_bookmark.domain.repository

import com.parkjin.github_bookmark.domain.model.User

interface GithubUserRepository {

    fun getUsers(name: String): List<User>
}
