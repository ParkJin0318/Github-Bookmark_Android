package com.parkjin.github_bookmark.presentation.ui.user

import com.parkjin.github_bookmark.domain.model.User

fun List<UserListItem>.toUserItems() =
    this.filterIsInstance<UserListItem.UserItem>()

fun List<UserListItem>.toHeaderItems() =
    this.filterIsInstance<UserListItem.UserHeader>()

fun List<User>.toUserListItems(bookmarkUser: (UserListItem.UserItem) -> Unit): List<UserListItem> {
    val userListItems: MutableList<UserListItem> = mutableListOf()

    this.sortedBy(User::name)
        .groupBy(User::header)
        .forEach { (header, users) ->
            userListItems.add(UserListItem.UserHeader(header))
            userListItems.addAll(users.map {
                UserListItem.UserItem(it, bookmarkUser::invoke)
            })
        }
    return userListItems
}

fun MutableList<UserListItem>.replaceUserItem(item: UserListItem.UserItem) {
    val position = this.indexOfFirst {
        if (it is UserListItem.UserItem) it.user.name == item.user.name
        else false
    }.takeIf { it != -1 } ?: return
    this[position] = item
}

fun MutableList<UserListItem>.addUserItem(item: UserListItem.UserItem) {
    this.toHeaderItems()
        .find { it.header == item.user.header }
        ?: this.add(UserListItem.UserHeader(item.user.header))

    this.add(item)
    this.sortBy { it.orderName }
}

fun MutableList<UserListItem>.removeUserItem(item: UserListItem.UserItem) {
    val position = this.indexOfFirst {
        if (it is UserListItem.UserItem) it.user.name == item.user.name
        else false
    }.takeIf { it != -1 } ?: return
    this.removeAt(position)

    this.toUserItems()
        .find { it.user.header == item.user.header }
        ?: let {
            this.toHeaderItems()
                .find { it.header == item.user.header }
                ?.let { this.remove(it) }
        }
}
