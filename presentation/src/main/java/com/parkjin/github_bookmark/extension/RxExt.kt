package com.parkjin.github_bookmark.extension

import com.parkjin.github_bookmark.provider.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

fun <T: Any> Observable<T>.onNetwork(scheduler: SchedulerProvider): Observable<T> =
    this.subscribeOn(scheduler.io)
        .observeOn(scheduler.ui)

fun <T: Any> Observable<T>.onDebounce(scheduler: SchedulerProvider): Observable<T> =
    this.debounce(500L, TimeUnit.MICROSECONDS, scheduler.computation)

fun <T: Any> Single<T>.onNetwork(scheduler: SchedulerProvider): Single<T> =
    this.subscribeOn(scheduler.io)
        .observeOn(scheduler.ui)

fun Completable.onNetwork(scheduler: SchedulerProvider): Completable =
    this.subscribeOn(scheduler.io)
        .observeOn(scheduler.ui)
