package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import io.reactivex.rxjava3.core.Completable

class BookmarkUserUseCase(
    private val repository: BookmarkUserRepository
) {

    fun execute(user: User, bookmarked: Boolean): Completable =
        if (bookmarked.not()) repository.bookmarkUser(user)
        else repository.unBookmarkUser(user)
}
