package com.parkjin.github_bookmark.presentation.ui.common

import com.parkjin.github_bookmark.domain.model.User

sealed interface UserListModel {

    val header: Char?

    data class HeaderModel(val value: Char?) : UserListModel {
        override val header: Char? = this.value
    }

    data class UserModel(
        val user: User,
        val toggleBookmark: (UserModel) -> Unit
    ) : UserListModel {
        override val header: Char? = user.name.firstOrNull()?.uppercaseChar()
    }

    object LoadingModel : UserListModel {
        override val header: Char? = null
    }
}
