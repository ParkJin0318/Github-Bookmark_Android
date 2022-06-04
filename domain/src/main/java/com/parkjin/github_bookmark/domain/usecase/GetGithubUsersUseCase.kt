package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import com.parkjin.github_bookmark.domain.repository.GithubUserRepository
import kotlinx.coroutines.Dispatchers
import com.parkjin.github_bookmark.domain.model.Result
import kotlinx.coroutines.flow.*

class GetGithubUsersUseCase(
    private val githubUserRepository: GithubUserRepository,
    private val bookmarkUserRepository: BookmarkUserRepository,
) {

    operator fun invoke(name: String): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)

        try {
            val users = flow<List<User>> { githubUserRepository.getUsers(name) }
                .flowOn(Dispatchers.IO)
                .zip(
                    flow<List<User>> { bookmarkUserRepository.getUsers(name) }
                        .flowOn(Dispatchers.IO)
                ) { githubUsers, bookmarkUsers ->
                    Result.Success(
                        githubUsers.map { user ->
                            val bookmarked = bookmarkUsers.map { it.name }.contains(user.name)
                            user.copy(bookmarked = bookmarked)
                        }
                    )
                }.flowOn(Dispatchers.Default)

            emitAll(users)
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}
