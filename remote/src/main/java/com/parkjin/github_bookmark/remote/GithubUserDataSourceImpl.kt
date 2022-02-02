package com.parkjin.github_bookmark.remote

import com.parkjin.github_bookmark.remote.api.GithubUserAPI
import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.remote.extension.onErrorBodyCatch
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.remote.response.toModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GithubUserDataSourceImpl @Inject constructor(
    private val api: GithubUserAPI
) : GithubUserDataSource {

    override fun getUsers(name: String): Single<List<User>> =
        api.getGithubUsers("$name+in:login")
            .onErrorBodyCatch()
            .map { it.items }
            .map { list ->
                list.map { it.toModel() }
            }
}
