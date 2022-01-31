package com.parkjin.github_bookmark.extension

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T: Any> Single<T>.subscribeOnIO(): Single<T> =
    this.subscribeOn(Schedulers.io())
