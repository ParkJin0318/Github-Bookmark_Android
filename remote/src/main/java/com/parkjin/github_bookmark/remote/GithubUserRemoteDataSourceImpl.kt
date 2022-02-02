package com.parkjin.github_bookmark.remote

import com.parkjin.github_bookmark.remote.api.GithubUserAPI
import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.data.model.GithubUser
import com.parkjin.github_bookmark.remote.extension.onErrorBodyCatch
import com.parkjin.github_bookmark.remote.response.toModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class GithubUserRemoteDataSourceImpl(
    private val api: GithubUserAPI
) : GithubUserDataSource {

    override fun getUsers(keyword: String): Single<List<GithubUser>> =
        api.getGithubUsers("$keyword+in:login")
            .onErrorBodyCatch()
            .map { it.items }
            .map { list ->
                list.map { it.toModel() }
            }

    override fun addUsers(keyword: String, users: List<GithubUser>): Completable =
        Completable.complete()

    override fun removeUsers(keyword: String): Completable =
        Completable.complete()
}
