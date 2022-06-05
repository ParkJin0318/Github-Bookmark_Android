package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import kotlinx.coroutines.Dispatchers
import com.parkjin.github_bookmark.domain.model.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class GetGithubUsersUseCase(
    private val githubUserRepository: GithubUserRepository,
    private val bookmarkUserRepository: BookmarkUserRepository,
) {

    operator fun invoke(name: String): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)

        try {
            val githubUsers = withContext(Dispatchers.IO) {
                githubUserRepository.getUsers(name)
            }
            val bookmarkUsers = withContext(Dispatchers.IO) {
                bookmarkUserRepository.getUsers(name)
            }

            emit(Result.Success(githubUsers.filteringBookmark(bookmarkUsers)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun List<User>.filteringBookmark(bookmarkUsers: List<User>) = this.map { user ->
        val bookmarked = bookmarkUsers.map { it.name }.contains(user.name)
        user.copy(bookmarked = bookmarked)
    }
}
