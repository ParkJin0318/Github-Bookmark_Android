package com.parkjin.github_bookmark.presentation.ui.user

import com.parkjin.github_bookmark.domain.model.User

sealed class UserListItem(val orderName: String?) {

    data class UserHeader(val header: String) : UserListItem(header)

    data class UserItem (
        val user: User,
        val toggleBookmark: (UserItem) -> Unit
    ) : UserListItem(user.header)

    object Loading : UserListItem(null)
}
