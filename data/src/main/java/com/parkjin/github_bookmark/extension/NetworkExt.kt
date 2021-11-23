package com.parkjin.github_bookmark.extension

import io.reactivex.rxjava3.core.Single
import retrofit2.Response

fun <T: Any> Single<Response<T>>.catch(): Single<T> {
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
