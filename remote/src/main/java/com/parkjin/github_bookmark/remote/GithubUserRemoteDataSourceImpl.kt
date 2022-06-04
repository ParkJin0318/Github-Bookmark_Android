package com.parkjin.github_bookmark.remote

import com.parkjin.github_bookmark.remote.api.GithubUserAPI
import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.data.model.GithubUser
import com.parkjin.github_bookmark.remote.response.GithubUserResponse
import com.parkjin.github_bookmark.remote.response.toModel

class GithubUserRemoteDataSourceImpl(
    private val api: GithubUserAPI
) : GithubUserDataSource {

    override fun getUsers(name: String): List<GithubUser> =
        api.getGithubUsers("$name+in:login")
            .execute()
            .body()
            ?.items
            ?.map(GithubUserResponse::toModel)
            ?: emptyList()
}
