package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class GetGithubUsersUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val githubUserRepository: GithubUserRepository,
    private val bookmarkUserRepository: BookmarkUserRepository
) {

    operator fun invoke(name: String): Flow<List<User>> {
        return githubUserRepository.getUsers(name)
            .flowOn(dispatcher)
            .combine(
                bookmarkUserRepository.getUsers(name)
                    .flowOn(dispatcher)
            ) { githubUsers, bookmarkUsers ->
                githubUsers.map { user ->
                    val bookmarked = bookmarkUsers.map { it.name }.contains(user.name)
                    user.copy(bookmarked = bookmarked)
                }
            }
    }
}
