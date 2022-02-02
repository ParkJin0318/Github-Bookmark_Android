package com.parkjin.github_bookmark.data.repository

import com.parkjin.github_bookmark.domain.repository.UserRepository
import com.parkjin.github_bookmark.data.datasource.BookmarkUserDataSource
import com.parkjin.github_bookmark.data.datasource.GithubUserDataSource
import com.parkjin.github_bookmark.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubUserDataSource: GithubUserDataSource,
    private val bookmarkUserDataSource: BookmarkUserDataSource
): UserRepository {

    override fun getGithubUsers(name: String): Single<List<User>> =
        githubUserDataSource.getUsers(name)

    override fun getBookmarkUsers(name: String): Single<List<User>> =
        bookmarkUserDataSource.getUsers(name)

    override fun bookmarkUser(user: User): Completable =
        bookmarkUserDataSource.bookmarkUser(user)

    override fun unBookmarkUser(user: User): Completable =
        bookmarkUserDataSource.unBookmarkUser(user)
}
