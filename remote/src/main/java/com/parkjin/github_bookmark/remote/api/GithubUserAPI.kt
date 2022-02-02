package com.parkjin.github_bookmark.remote.api

import com.parkjin.github_bookmark.remote.response.GithubResponse
import com.parkjin.github_bookmark.remote.response.GithubUser
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_PAGE = 1
private const val DEFAULT_PER_PAGE = 100

interface GithubUserAPI {

    @GET("search/users")
    fun getGithubUsers(
        @Query("q", encoded = true) name: String,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE
    ): Single<Response<GithubResponse<GithubUser>>>
}
