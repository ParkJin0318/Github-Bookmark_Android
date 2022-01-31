package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class BookmarkUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    fun execute(user: User, bookmarked: Boolean): Completable =
        if (bookmarked.not()) repository.bookmarkUser(user)
        else repository.unBookmarkUser(user)
}
