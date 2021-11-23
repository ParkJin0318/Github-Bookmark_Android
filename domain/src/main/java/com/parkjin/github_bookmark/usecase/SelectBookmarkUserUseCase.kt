package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.rxjava3.core.Completable

class SelectBookmarkUserUseCase(
    private val repository: UserRepository
) {
    fun execute(user: User): Completable =
        if (!user.isBookmark) repository.addBookmarkUser(user)
        else repository.deleteBookmarkUser(user)
}
