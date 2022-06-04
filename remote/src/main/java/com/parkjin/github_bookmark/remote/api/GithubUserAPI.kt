package com.parkjin.github_bookmark.remote.api

import com.parkjin.github_bookmark.remote.response.GithubResponse
import com.parkjin.github_bookmark.remote.response.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubUserAPI {

    companion object {
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_PER_PAGE = 100
    }

    @GET("search/users")
    fun getGithubUsers(
        @Query("q", encoded = true) keyword: String,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE
    ): GithubResponse<GithubUserResponse>
}
