package com.parkjin.github_bookmark.ui.user

import com.parkjin.github_bookmark.model.UserType
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object BookmarkStore {

    private val notifier: PublishSubject<Unit> = PublishSubject.create()

    var userType: UserType = UserType.GITHUB
        private set

    var userItem: UserListItem.UserItem? = null
        private set

    fun register(): Observable<Unit> = notifier

    fun update(userType: UserType, userItem: UserListItem.UserItem) {
        this.userType = userType
        this.userItem = userItem

        notifier.onNext(Unit)
    }
}
