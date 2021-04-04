package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.extension.with
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Completable

/**
 * Local의 즐겨찾기 사용자를 추가하는 UseCase 클래스
 */
class AddBookmarkUserUseCase(
    private val repository: UserRepository
) {
    fun execute(user: User): Completable =
        repository.addBookmarkUser(user)
            .with()
}