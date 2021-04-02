package com.parkjin.github_bookmark.extension

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ReactiveX Extension 함수
 */
fun <T> Single<T>.with(): Single<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.with(): Completable =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())