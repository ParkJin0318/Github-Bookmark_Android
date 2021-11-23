package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.sort
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.rxjava3.core.Single

class GetUsersUseCase(
    private val repository: UserRepository
) {
    fun execute(name: String): Single<List<User>> =
        Single.zip(
            repository.getUsersForName(name),
            repository.getBookmarkUsers()
        ) { users, bookmarkUsers ->

            users.map { user ->
                user.bookmarked(
                    bookmarkUsers
                        .map { it.name }
                        .contains(user.name)
                )
            }.sort()
        }
}
