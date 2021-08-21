package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.sort
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Single

/**
 * API 이용하여 사용자를 정보를 조회하는 UseCase 클래스
 */
class GetUsersUseCase(
    private val repository: UserRepository
) {
    fun execute(name: String): Single<List<User>> =
        Single.zip(
            repository.getUsersForName(name),
            repository.getBookmarkUsers(), { users, bookmarkUsers ->

                val newUsers = users.map { user ->
                    val isBookmark = bookmarkUsers.map { it.name }
                        .contains(user.name)

                    user.bookmarked(isBookmark)
                }
                newUsers.sort()
            }
        )
}