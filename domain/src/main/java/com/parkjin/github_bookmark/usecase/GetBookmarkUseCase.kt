package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.base.UseCase
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Single

/*
    Repository 데이터를 비즈니스 로직에 맞게 변환,
    Local의 즐겨찾기 사용자를 조회하는 클래스
 */
class GetBookmarkUseCase(
    private val repository: UserRepository
): UseCase<String, List<User>>() {

    override fun create(data: String): Single<List<User>> =
        repository.getAllBookmarkUser(data)
}