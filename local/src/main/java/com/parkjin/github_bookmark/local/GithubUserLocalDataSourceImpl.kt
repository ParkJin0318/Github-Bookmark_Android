package com.parkjin.github_bookmark.local

import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.data.model.GithubUser
import com.parkjin.github_bookmark.local.dao.GithubUserDao
import com.parkjin.github_bookmark.local.entity.toEntity
import com.parkjin.github_bookmark.local.entity.toModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class GithubUserLocalDataSourceImpl(
    private val dao: GithubUserDao
) : GithubUserDataSource {

    override fun getUsers(keyword: String): Single<List<GithubUser>> =
        dao.getGithubUsers(keyword)
            .map { list ->
                list.map { it.toModel() }
            }

    override fun addUsers(keyword: String, users: List<GithubUser>): Completable =
        dao.insertGithubUsers(
            users.map { it.toEntity(keyword) }
        )

    override fun removeUsers(keyword: String): Completable =
        dao.deleteGithubUsers(keyword)
}
