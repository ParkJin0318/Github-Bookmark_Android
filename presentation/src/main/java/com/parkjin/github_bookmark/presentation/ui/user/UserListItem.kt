package com.parkjin.github_bookmark.presentation.ui.user

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.model.header

enum class UserListType {
    USER_HEADER,
    USER_ITEM,
    LOADING;

    companion object {
        fun from(ordinal: Int) = values().find { it.ordinal == ordinal } ?: LOADING
    }
}

sealed class UserListItem {

    abstract val itemType: Int
    abstract val itemName: String

    data class UserHeader(val header: String) : UserListItem() {
        override val itemType = UserListType.USER_HEADER.ordinal
        override val itemName = header
    }

    data class UserItem(
        val user: User,
        var bookmarked: Boolean = user.bookmarked
    ) : UserListItem() {
        override val itemType = UserListType.USER_ITEM.ordinal
        override val itemName = user.header
    }

    object Loading : UserListItem() {
        override val itemType = UserListType.LOADING.ordinal
        override val itemName = ""
    }
}
