package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.Result
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetBookmarkUsersUseCase(
    private val bookmarkUserRepository: BookmarkUserRepository
) {

    operator fun invoke(name: String): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)

        try {
            val users = bookmarkUserRepository.getUsers(name)
            emit(Result.Success(users))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
