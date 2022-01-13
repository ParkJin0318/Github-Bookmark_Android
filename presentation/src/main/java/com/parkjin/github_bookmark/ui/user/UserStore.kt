package com.parkjin.github_bookmark.ui.user

import com.parkjin.github_bookmark.model.UserType
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object UserStore {

    private val notifier: PublishSubject<Unit> = PublishSubject.create()

    private var _userType: UserType = UserType.GITHUB
    val userType: UserType get() = _userType

    private var _userItem: UserListItem.UserItem? = null
    val userItem: UserListItem.UserItem get() = _userItem!!

    fun register(): Observable<Unit> = notifier

    fun update(userType: UserType, userItem: UserListItem.UserItem) {
        _userType = userType
        _userItem = userItem

        notifier.onNext(Unit)
    }

}
