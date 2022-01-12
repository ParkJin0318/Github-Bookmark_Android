package com.parkjin.github_bookmark.ui.user

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.UserType
import io.reactivex.rxjava3.subjects.PublishSubject

object BookmarkStore {

    private val bookmarkUserSubject: PublishSubject<Pair<UserType, User>> = PublishSubject.create()

    fun register() = bookmarkUserSubject

    fun update(data: Pair<UserType, User>) {
        bookmarkUserSubject.onNext(data)
    }
}