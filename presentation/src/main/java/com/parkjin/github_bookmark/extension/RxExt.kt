package com.parkjin.github_bookmark.extension

import com.parkjin.github_bookmark.provider.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

fun <T: Any> Observable<T>.onNetwork(scheduler: SchedulerProvider) =
    this.subscribeOn(scheduler.io)
        .observeOn(scheduler.ui)

fun <T: Any> Single<T>.onNetwork(scheduler: SchedulerProvider) =
    this.subscribeOn(scheduler.io)
        .observeOn(scheduler.ui)

fun Completable.onNetwork(scheduler: SchedulerProvider) =
    this.subscribeOn(scheduler.io)
        .observeOn(scheduler.ui)
