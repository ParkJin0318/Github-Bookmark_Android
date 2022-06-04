package com.parkjin.github_bookmark.presentation.ui.user

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.parkjin.github_bookmark.component.header.HeaderView
import com.parkjin.github_bookmark.component.loading.LoadingView
import com.parkjin.github_bookmark.component.user.UserItemView
import com.parkjin.github_bookmark.presentation.R

class UserListAdapter : ListAdapter<UserListItem, UserListViewHolder>(DiffItemCallback()) {

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is UserListItem.UserHeader -> R.layout.view_header
        is UserListItem.UserItem -> R.layout.view_user_item
        is UserListItem.Loading -> R.layout.view_loading
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder =
        when (viewType) {
            R.layout.view_header ->
                UserListViewHolder.UserHeaderViewHolder(HeaderView(parent.context))
            R.layout.view_user_item ->
                UserListViewHolder.UserItemViewHolder(UserItemView(parent.context))
            else ->
                UserListViewHolder.LoadingViewHolder(LoadingView(parent.context))
        }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is UserListViewHolder.UserHeaderViewHolder ->
                holder.bind(item as UserListItem.UserHeader)
            is UserListViewHolder.UserItemViewHolder ->
                holder.bind(item as UserListItem.UserItem)
            else -> return
        }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<UserListItem>() {

        override fun areItemsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean {
            val isSameHeader = oldItem is UserListItem.UserHeader
                    && newItem is UserListItem.UserHeader
                    && oldItem.header == newItem.header

            val isSameUser = oldItem is UserListItem.UserItem
                    && newItem is UserListItem.UserItem
                    && oldItem.user.name == newItem.user.name
                    && oldItem.user.bookmarked == newItem.user.bookmarked

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
}
