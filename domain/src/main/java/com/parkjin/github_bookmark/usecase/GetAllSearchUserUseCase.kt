package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.base.UseCase
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Single

/**
 * Repository 데이터를 비즈니스 로직에 맞게 변환,
 * API를 사용하여 사용자를 정보를 조회하는 클래스
 */
class GetAllSearchUserUseCase(
    private val repository: UserRepository
): UseCase<String, List<User>>() {

    override fun create(data: String): Single<List<User>> =
        repository.getAllSearchUser(data)
}