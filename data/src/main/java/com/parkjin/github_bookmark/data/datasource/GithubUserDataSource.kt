package com.parkjin.github_bookmark.data.datasource

import com.parkjin.github_bookmark.data.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUserDataSource {

    fun getUsers(name: String): Flow<List<GithubUser>>
}
