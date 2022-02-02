package com.parkjin.github_bookmark.data.repository

import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.data.extension.diffHour
import com.parkjin.github_bookmark.data.extension.isPassedDay
import com.parkjin.github_bookmark.data.model.GithubUser
import com.parkjin.github_bookmark.data.model.toUser
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import io.reactivex.rxjava3.core.Single
import java.util.*

class GithubUserRepositoryImpl(
    private val remoteDataSource: GithubUserDataSource,
    private val localDataSource: GithubUserDataSource
) : GithubUserRepository {

    override fun getUsers(keyword: String): Single<List<User>> =
        localDataSource.getUsers(keyword)
            .flatMap {
                if (it.isEmpty() || it.firstOrNull()?.savedAt.isPassedDay()) {
                    Single.error(Throwable("Not Refreshed"))
                } else {
                    Single.just(it)
                }
            }.onErrorResumeNext {
                remoteDataSource.getUsers(keyword)
                    .flatMap {
                        localDataSource.removeUsers(keyword)
                            .andThen(localDataSource.addUsers(keyword, it))
                            .toSingleDefault(it)
                    }
            }.map { list ->
                list.map { it.toUser() }
            }
}
