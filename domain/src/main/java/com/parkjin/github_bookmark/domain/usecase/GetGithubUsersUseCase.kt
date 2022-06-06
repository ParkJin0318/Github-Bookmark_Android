package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import kotlinx.coroutines.Dispatchers
import com.parkjin.github_bookmark.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*

class GetGithubUsersUseCase(
    private val githubUserRepository: GithubUserRepository,
    private val bookmarkUserRepository: BookmarkUserRepository,
) {

    operator fun invoke(name: String): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)

        try {
            val asyncGithubUsers = CoroutineScope(Dispatchers.IO).async {
                githubUserRepository.getUsers(name)
            }
            val asyncBookmarkUsers = CoroutineScope(Dispatchers.IO).async {
                bookmarkUserRepository.getUsers(name)
            }

            val filteringUsers = CoroutineScope(Dispatchers.Default).async {
                val githubUsers = asyncGithubUsers.await()
                val bookmarkUsers = asyncBookmarkUsers.await()

                githubUsers.map { user ->
                    val bookmarked = bookmarkUsers.map { it.name }.contains(user.name)
                    user.copy(bookmarked = bookmarked)
                }
            }

            emit(Result.Success(filteringUsers.await()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
