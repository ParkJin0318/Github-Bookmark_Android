package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.sort
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.rxjava3.core.Single

class GetBookmarkUsersUseCase(
    private val repository: UserRepository
) {
    fun execute(name: String): Single<List<User>> {
        val newUsers =
            if (name.isEmpty()) repository.getBookmarkUsers()
            else repository.getBookmarkUsersForName(name)

        return newUsers.map { users ->
                users.map { it.copy(bookmarked = true) }
            }.map { it.sort() }
    }
}
