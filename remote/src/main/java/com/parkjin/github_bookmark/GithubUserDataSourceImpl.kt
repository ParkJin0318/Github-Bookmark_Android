package com.parkjin.github_bookmark

import com.parkjin.github_bookmark.api.GithubUserAPI
import com.parkjin.github_bookmark.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.extension.onErrorBodyCatch
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.response.toModel
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
