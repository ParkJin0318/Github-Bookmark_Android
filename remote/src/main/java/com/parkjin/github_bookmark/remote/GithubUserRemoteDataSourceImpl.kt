package com.parkjin.github_bookmark.remote

import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.data.model.GithubUser
import com.parkjin.github_bookmark.remote.api.GithubUserApi
import com.parkjin.github_bookmark.remote.response.GithubUserResponse
import com.parkjin.github_bookmark.remote.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubUserRemoteDataSourceImpl(
    private val api: GithubUserApi
) : GithubUserDataSource {

    override fun getUsers(name: String): Flow<List<GithubUser>> {
        return flow {
            emit(
                api.getGithubUsers("$name+in:login")
                    .items
                    .map(GithubUserResponse::toModel)
            )
        }
    }
}
