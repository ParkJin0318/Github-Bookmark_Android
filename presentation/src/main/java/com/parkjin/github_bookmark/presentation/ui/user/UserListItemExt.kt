package com.parkjin.github_bookmark.presentation.ui.user

import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.model.header

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
    this.sortBy { it.itemName }
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
