package com.parkjin.github_bookmark.extension

import com.parkjin.github_bookmark.provider.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

fun <T: Any> Single<T>.subscribe(
    scheduler: SchedulerProvider,
    disposable: CompositeDisposable,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    this.observeOn(scheduler.ui)
        .subscribe(onSuccess::invoke, onError::invoke)
        .addTo(disposable)
}

fun Completable.subscribe(
    scheduler: SchedulerProvider,
    disposable: CompositeDisposable,
    onSuccess: () -> Unit,
    onError: (Throwable) -> Unit
) {
    this.observeOn(scheduler.ui)
        .subscribe(onSuccess::invoke, onError::invoke)
        .addTo(disposable)
}
