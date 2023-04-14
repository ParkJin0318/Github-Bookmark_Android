package com.parkjin.github_bookmark.remote.api

import com.parkjin.github_bookmark.remote.response.GithubResponse
import com.parkjin.github_bookmark.remote.response.GithubUserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GithubUserApiImpl(private val httpClient: HttpClient) : GithubUserApi {

    override suspend fun getGithubUsers(
        keyword: String,
        page: Int,
        perPage: Int
    ): GithubResponse<GithubUserResponse> = httpClient.get("search/users") {
        url {
            encodedParameters.append("q", keyword)
            parameters.append("page", "$page")
            parameters.append("per_page", "$perPage")
        }
    }.body()
}
