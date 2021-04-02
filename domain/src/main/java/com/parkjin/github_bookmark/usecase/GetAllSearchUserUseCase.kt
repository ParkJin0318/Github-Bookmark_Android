package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.extension.with
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Single

/**
 * API 이용하여 사용자를 정보를 조회하는 UseCase 클래스
 */
class GetAllSearchUserUseCase(
    private val repository: UserRepository
) {
    fun execute(name: String): Single<List<User>> =
        repository.getAllSearchUser(name)
            .with()
}