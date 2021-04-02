package com.parkjin.github_bookmark.network.api

import com.parkjin.github_bookmark.network.response.Response
import com.parkjin.github_bookmark.network.response.UserData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Github User 조회 API
 */
interface UserAPI {
    @GET("search/users")
    fun getAllUser(
        @Query("q") name: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<retrofit2.Response<Response<List<UserData>>>>
}