package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.provider.SchedulerProvider
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class BookmarkUserUseCase @Inject constructor(
    private val scheduler: SchedulerProvider,
    private val repository: UserRepository
) {
    fun execute(user: User, bookmarked: Boolean): Completable {
        val bookmarkUser =
            if (bookmarked.not()) repository.addBookmarkUser(user)
            else repository.deleteBookmarkUser(user)

        return bookmarkUser.subscribeOn(scheduler.io)
    }

}
