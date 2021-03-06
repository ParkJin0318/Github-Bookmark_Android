package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.Result
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BookmarkUserUseCase(
    private val repository: BookmarkUserRepository
) {

    operator fun invoke(user: User): Flow<Result<Unit>> = flow {
        emit(Result.Loading)

        try {
            val bookmark =
                if (user.bookmarked) repository.bookmarkUser(user)
                else repository.unBookmarkUser(user)

            emit(Result.Success(bookmark))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
