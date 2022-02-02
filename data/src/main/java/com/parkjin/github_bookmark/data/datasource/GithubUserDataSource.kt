package com.parkjin.github_bookmark.data.datasource

import com.parkjin.github_bookmark.data.model.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface GithubUserDataSource {

    fun getUsers(keyword: String): Single<List<GithubUser>>

    fun addUsers(keyword: String, users: List<GithubUser>): Completable

    fun removeUsers(keyword: String): Completable
}
