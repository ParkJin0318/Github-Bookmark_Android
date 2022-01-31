package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.extension.subscribeOnIO
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.UserType
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    fun execute(name: String, type: UserType): Single<List<User>> =
        when (type) {
            UserType.GITHUB ->
                Single.zip(
                    repository.getGithubUsers(name)
                        .subscribeOnIO(),
                    repository.getBookmarkUsers(name)
                        .subscribeOnIO()
                ) { githubUsers, bookmarkUsers ->
                    githubUsers.map { user ->
                        val bookmarked = bookmarkUsers.map { it.name }.contains(user.name)
                        user.copy(bookmarked = bookmarked)
                    }
                }

            UserType.BOOKMARK ->
                repository.getBookmarkUsers(name)
                    .map { users ->
                        users.map { it.copy(bookmarked = true) }
                    }
        }
}
