package com.parkjin.github_bookmark.ui.user

import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.header

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
    abstract val name: String

    data class UserHeader(val header: String) : UserListItem() {
        override val type = UserListType.USER_HEADER
        override val name = header
    }

    data class UserItem(
        val user: User,
        var bookmarked: Boolean = user.bookmarked
    ) : UserListItem() {
        override val type = UserListType.USER_ITEM
        override val name = user.header
    }

    object Loading : UserListItem() {
        override val type = UserListType.LOADING
        override val name = ""
    }
}

fun List<UserListItem>.toUserItems() =
    this.filterIsInstance<UserListItem.UserItem>()

fun List<UserListItem>.toHeaderItems() =
    this.filterIsInstance<UserListItem.UserHeader>()

fun List<User>.toUserListItems(): List<UserListItem> {
    val userListItems: MutableList<UserListItem> = mutableListOf()

    this.sortedBy(User::name)
        .groupBy(User::header)
        .forEach { (header, users) ->
            userListItems.add(UserListItem.UserHeader(header))
            userListItems.addAll(users.map(UserListItem::UserItem))
        }
    return userListItems
}

fun MutableList<UserListItem>.replaceUserItem(position: Int, item: UserListItem.UserItem) {
    this[position] = item
}

fun MutableList<UserListItem>.addUserItem(item: UserListItem.UserItem) {
    this.toHeaderItems()
        .find { it.header == item.user.header }
        ?: this.add(UserListItem.UserHeader(item.user.header))

    this.add(item)
    this.sortBy { it.name }
}

fun MutableList<UserListItem>.removeUserItem(item: UserListItem.UserItem) {
    this.remove(item)

    this.toUserItems()
        .find { it.user.header == item.user.header }
        ?: let {
            this.toHeaderItems()
                .find { it.header == item.user.header }
                ?.let { this.remove(it) }
        }
}
