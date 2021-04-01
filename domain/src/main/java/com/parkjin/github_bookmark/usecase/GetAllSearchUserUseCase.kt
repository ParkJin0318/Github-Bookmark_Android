package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * API 이용하여 사용자를 정보를 조회하는 UseCase 클래스
 */
class GetAllSearchUserUseCase(
    private val repository: UserRepository
) {
    fun execute(name: String): Single<List<User>> =
        repository.getAllSearchUser(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}