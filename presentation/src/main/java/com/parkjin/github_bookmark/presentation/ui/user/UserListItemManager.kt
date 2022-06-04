package com.parkjin.github_bookmark.presentation.ui.user

import com.parkjin.github_bookmark.presentation.ui.main.MainTabType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object UserListItemManager {

    private val notifier: MutableStateFlow<Unit> = MutableStateFlow(Unit)

    var tabType: MainTabType = MainTabType.GITHUB
        private set

    var userItem: UserListItem.UserItem? = null
        private set

    fun register(): Flow<Unit> = notifier

    fun onNext(tabType: MainTabType, userItem: UserListItem.UserItem) {
        this.tabType = tabType
        this.userItem = userItem
        notifier.value = Unit
    }
}
