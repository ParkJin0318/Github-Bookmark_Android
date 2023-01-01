package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BookmarkUserUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: BookmarkUserRepository
) {

    suspend operator fun invoke(user: User) {
        withContext(dispatcher) {
            if (user.bookmarked) {
                repository.activateUserBookmark(user)
            } else {
                repository.disableUserBookmark(user)
            }
        }
    }
}
