package com.parkjin.github_bookmark.presentation.ui.common

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.presentation.ui.main.MainTabType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

object BookmarkObserver {

    private val state = MutableStateFlow<Data?>(null)

    fun registerForGithubTab(): Flow<User> = state.filterNotNull()
        .filter { it.tabType == MainTabType.BOOKMARK }
        .map { it.user }

    fun registerForBookmarkTab(): Flow<User> = state.filterNotNull()
        .filter { it.tabType == MainTabType.GITHUB }
        .map { it.user }

    suspend fun emitFoGithubTab(user: User) {
        state.emit(Data(MainTabType.GITHUB, user))
    }

    suspend fun emitForBookmarkTab(user: User) {
        state.emit(Data(MainTabType.BOOKMARK, user))
    }

    data class Data(
        val tabType: MainTabType,
        val user: User
    )
}
