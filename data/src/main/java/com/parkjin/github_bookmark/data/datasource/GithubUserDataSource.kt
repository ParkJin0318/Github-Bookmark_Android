package com.parkjin.github_bookmark.data.datasource

import com.parkjin.github_bookmark.data.model.GithubUser

interface GithubUserDataSource {

    suspend fun getUsers(name: String): List<GithubUser>
}
