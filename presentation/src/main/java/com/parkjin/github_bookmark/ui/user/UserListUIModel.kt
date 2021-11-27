package com.parkjin.github_bookmark.ui.user

import com.parkjin.github_bookmark.model.User

enum class UserListType {
    USER_HEADER,
    USER_ITEM,
    LOADING;

    companion object {
        fun get(ordinal: Int) = values().find { it.ordinal == ordinal } ?: LOADING
    }
}

sealed class UserListUIModel {

    data class UserHeader(val header: String) : UserListUIModel()

    data class UserItem(val user: User) : UserListUIModel()

    object Loading : UserListUIModel()
}

fun UserListUIModel.toViewType() = when(this) {
    is UserListUIModel.UserHeader -> UserListType.USER_HEADER.ordinal
    is UserListUIModel.UserItem -> UserListType.USER_ITEM.ordinal
    is UserListUIModel.Loading -> UserListType.LOADING.ordinal
}
