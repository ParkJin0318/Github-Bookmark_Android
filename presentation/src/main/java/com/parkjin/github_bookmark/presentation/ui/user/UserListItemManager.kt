package com.parkjin.github_bookmark.presentation.ui.user

import com.parkjin.github_bookmark.presentation.ui.main.MainTabType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object UserListItemManager {

    private val notifier: MutableSharedFlow<Unit> = MutableSharedFlow()

    var tabType: MainTabType = MainTabType.GITHUB
        private set

    var userItem: UserListItem.UserItem? = null
        private set

    fun register(): SharedFlow<Unit> = notifier

    suspend fun emit(tabType: MainTabType, userItem: UserListItem.UserItem) {
        this.tabType = tabType
        this.userItem = userItem
        notifier.emit(Unit)
    }
}
