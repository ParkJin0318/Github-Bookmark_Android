package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.extension.with
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Single

/**
 * Local의 즐겨찾기 사용자를 조회하는 UseCase 클래스
 */
class GetAllBookmarkUserUseCase(
    private val repository: UserRepository
) {
    fun execute(name: String): Single<List<User>> =
       repository.getAllBookmarkUser(name)
         .with()
}