package com.parkjin.github_bookmark.presentation.ui.common

import com.parkjin.github_bookmark.domain.model.User

sealed class UserListModel(val orderName: String?) {

    data class Header(val header: String) : UserListModel(header)

    data class Item (
        val user: User,
        val toggleBookmark: (Item) -> Unit
    ) : UserListModel(user.header)

    object Loading : UserListModel(null)
}
