package com.parkjin.github_bookmark.remote.api

import com.parkjin.github_bookmark.remote.response.GithubResponse
import com.parkjin.github_bookmark.remote.response.GithubUserResponse

interface GithubUserApi {

    companion object {
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_PER_PAGE = 100
    }

    suspend fun getGithubUsers(
        keyword: String,
        page: Int = DEFAULT_PAGE,
        perPage: Int = DEFAULT_PER_PAGE
    ): GithubResponse<GithubUserResponse>
}
