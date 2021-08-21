package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Completable

/**
 * Local의 즐겨찾기 사용자를 추가하는 UseCase 클래스
 */
class SelectBookmarkUserUseCase(
    private val repository: UserRepository
) {
    fun execute(user: User): Completable =
        if (!user.isBookmark) repository.addBookmarkUser(user)
        else repository.deleteBookmarkUser(user)
}