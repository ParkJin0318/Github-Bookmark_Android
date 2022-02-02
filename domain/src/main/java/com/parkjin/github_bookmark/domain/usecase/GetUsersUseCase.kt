package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.model.UserType
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GetUsersUseCase(
    private val githubUserRepository: GithubUserRepository,
    private val bookmarkUserRepository: BookmarkUserRepository,
) {

    fun execute(name: String, type: UserType): Single<List<User>> =
        when (type) {
            UserType.GITHUB ->
                Single.zip(
                    githubUserRepository.getUsers(name)
                        .subscribeOn(Schedulers.io()),
                    bookmarkUserRepository.getUsers(name)
                        .subscribeOn(Schedulers.io())
                ) { githubUsers, bookmarkUsers ->
                    githubUsers.map { user ->
                        val bookmarked = bookmarkUsers.map { it.name }.contains(user.name)
                        user.copy(bookmarked = bookmarked)
                    }
                }

            UserType.BOOKMARK ->
                bookmarkUserRepository.getUsers(name)
        }
}
