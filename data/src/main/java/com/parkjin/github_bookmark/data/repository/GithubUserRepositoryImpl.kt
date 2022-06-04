package com.parkjin.github_bookmark.data.repository

import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.data.model.GithubUser
import com.parkjin.github_bookmark.data.model.toUser
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository

class GithubUserRepositoryImpl(
    private val remoteDataSource: GithubUserDataSource
) : GithubUserRepository {

    override fun getUsers(name: String): List<User> =
        remoteDataSource.getUsers(name)
            .map(GithubUser::toUser)
}
