package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.sort
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Single

/**
 * Local의 즐겨찾기 사용자를 조회하는 UseCase 클래스
 */
class GetBookmarkUsersUseCase(
    private val repository: UserRepository
) {
    fun execute(name: String): Single<List<User>> {
        val newUsers =
            if (name.isEmpty()) repository.getBookmarkUsers()
            else repository.getBookmarkUsersForName(name)

        return newUsers.map { users ->
            users.map { it.bookmarked(true) }
        }.map { it.sort() }
    }
}