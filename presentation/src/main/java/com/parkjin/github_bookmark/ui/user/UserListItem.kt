package com.parkjin.github_bookmark.ui.user

import com.parkjin.github_bookmark.model.User

enum class UserListType {
    USER_HEADER,
    USER_ITEM,
    LOADING;

    companion object {
        fun from(ordinal: Int) = values().find { it.ordinal == ordinal } ?: LOADING
    }
}

sealed class UserListItem {

    abstract val type: UserListType

    data class UserHeader(val header: String) : UserListItem() {
        override val type = UserListType.USER_HEADER
    }

    data class UserItem(
        val user: User,
        var bookmarked: Boolean = user.bookmarked
    ) : UserListItem() {
        override val type = UserListType.USER_ITEM
    }

    object Loading : UserListItem() {
        override val type = UserListType.LOADING
    }
}
