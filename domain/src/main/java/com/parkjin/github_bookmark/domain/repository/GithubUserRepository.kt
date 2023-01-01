package com.parkjin.github_bookmark.domain.repository

import com.parkjin.github_bookmark.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {

    fun getUsers(name: String): Flow<List<User>>
}
