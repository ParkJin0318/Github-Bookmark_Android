package com.parkjin.github_bookmark.ui.user

import androidx.recyclerview.widget.DiffUtil

class UserListDiffCallback : DiffUtil.ItemCallback<UserListItem>() {

    override fun areItemsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean {
        val isSameHeader = oldItem is UserListItem.UserHeader
                && newItem is UserListItem.UserHeader
                && oldItem.header == newItem.header

        val isSameUser = oldItem is UserListItem.UserItem
                && newItem is UserListItem.UserItem
                && oldItem.user.name == newItem.user.name
                && oldItem.bookmarked == newItem.bookmarked

        val isSameLoading = oldItem is UserListItem.Loading
                && newItem is UserListItem.Loading
                && oldItem == newItem

        return isSameHeader || isSameUser || isSameLoading
    }

    override fun areContentsTheSame(
        oldItem: UserListItem,
        newItem: UserListItem
    ) = oldItem == newItem
}
