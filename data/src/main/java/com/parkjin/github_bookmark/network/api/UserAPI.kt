package com.parkjin.github_bookmark.network.api

import com.parkjin.github_bookmark.network.response.Response
import com.parkjin.github_bookmark.network.response.UserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_PAGE = 1
private const val DEFAULT_PER_PAGE = 100

interface UserAPI {

    @GET("search/users")
    fun getUsersForName(
        @Query("q") name: String,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE
    ): Single<retrofit2.Response<Response<List<UserResponse>>>>
}
