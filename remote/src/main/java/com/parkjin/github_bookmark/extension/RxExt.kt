package com.parkjin.github_bookmark.extension

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit

fun <T: Any> Observable<T>.onNetwork(): Observable<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T: Any> Observable<T>.onDebounce(): Observable<T> =
    this.debounce(500L, TimeUnit.MICROSECONDS, Schedulers.computation())

fun <T: Any> Single<T>.onNetwork(): Single<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.onNetwork(): Completable =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T: Any> Single<Response<T>>.onErrorBodyCatch(): Single<T> {
    return this.flatMap {
        if (it.isSuccessful) {
            val body = it.body()

            if (body == null) {
                Single.error(Throwable("Not Found Response Body!"))
            } else {
                Single.just(body)
            }
        } else {
            val errorBody = it.errorBody()?.string()

            if (errorBody == null) {
                Single.error(Throwable("Not Found Response Error Body!"))
            } else {
                Single.error(Throwable(errorBody))
            }
        }
    }
}
