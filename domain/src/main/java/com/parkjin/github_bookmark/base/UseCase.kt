package com.parkjin.github_bookmark.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
    UseCase 클래스의 보일러 플레이트 코드를 제거하기 위한 Base 클래스
 */
abstract class UseCase<I, O> {

    abstract fun create(data: I): Single<O>

    fun execute(data: I): Single<O> {
        return create(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}