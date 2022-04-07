package com.parkjin.github_bookmark.presentation.ui.user

import com.parkjin.github_bookmark.domain.model.UserType
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object UserListItemManager {

    private val notifier: PublishSubject<Unit> = PublishSubject.create()

    var userType: UserType = UserType.GITHUB
        private set

    var userItem: UserListItem.UserItem? = null
        private set

    fun register(): Observable<Unit> = notifier

    fun onNext(userType: UserType, userItem: UserListItem.UserItem) {
        this.userType = userType
        this.userItem = userItem

        notifier.onNext(Unit)
    }
}