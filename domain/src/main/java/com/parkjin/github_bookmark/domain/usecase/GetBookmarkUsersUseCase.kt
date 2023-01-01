package com.parkjin.github_bookmark.domain.usecase

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.repository.BookmarkUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetBookmarkUsersUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val bookmarkUserRepository: BookmarkUserRepository
) {

    operator fun invoke(name: String): Flow<List<User>> {
        return bookmarkUserRepository.getUsers(name)
            .flowOn(dispatcher)
    }
}
