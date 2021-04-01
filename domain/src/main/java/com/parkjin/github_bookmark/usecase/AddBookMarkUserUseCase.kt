package com.parkjin.github_bookmark.usecase

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Local의 즐겨찾기 사용자를 추가하는 UseCase 클래스
 */
class AddBookMarkUserUseCase(
    private val repository: UserRepository
) {
    fun execute(user: User): Completable =
        repository.addBookmarkUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}