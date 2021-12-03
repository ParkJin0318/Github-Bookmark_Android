package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.UserType
import com.parkjin.github_bookmark.provider.SchedulerProvider
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val scheduler: SchedulerProvider,
    private val repository: UserRepository
) {
    fun execute(name: String, type: UserType, page: Int = 1): Single<List<User>> {
        val users = when (type) {
            UserType.GITHUB -> getGithubUser(name, page)
            UserType.BOOKMARK -> getBookmarkUser(name)
        }

        return users.subscribeOn(scheduler.io)
    }

    private fun getGithubUser(name: String, page: Int): Single<List<User>> {
        val getGithubUsers = repository.getUsersForName(name, page)
            .subscribeOn(scheduler.io)

        val getBookmarkUsers = repository.getBookmarkUsers()
            .subscribeOn(scheduler.io)

        return Single.zip(getGithubUsers, getBookmarkUsers) { users, bookmarkUsers ->
            users.map { user ->
                val bookmarked = bookmarkUsers.map { it.name }.contains(user.name)
                user.copy(bookmarked = bookmarked)
            }
        }
    }

    private fun getBookmarkUser(name: String): Single<List<User>> {
        val bookmarkUsers =
            if (name.isEmpty()) repository.getBookmarkUsers()
            else repository.getBookmarkUsersForName(name)

        return bookmarkUsers.map { users ->
            users.map { it.copy(bookmarked = true) }
        }
    }

}
