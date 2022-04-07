package com.parkjin.github_bookmark.presentation.ui.user

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.parkjin.github_bookmark.component.header.HeaderView
import com.parkjin.github_bookmark.component.loading.LoadingView
import com.parkjin.github_bookmark.component.user.UserItemView

class UserListAdapter(
    private val onStarredClick: (String) -> Unit
) : ListAdapter<UserListItem, UserListViewHolder>(DiffItemCallback()) {

    override fun getItemViewType(position: Int) = getItem(position).itemType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder =
        when (UserListType.from(viewType)) {
            UserListType.USER_HEADER -> UserListViewHolder.UserHeaderViewHolder(
                HeaderView(parent.context)
            )
            UserListType.USER_ITEM -> UserListViewHolder.UserItemViewHolder(
                UserItemView(parent.context),
                onStarredClick::invoke
            )
            UserListType.LOADING -> UserListViewHolder.LoadingViewHolder(
                LoadingView(parent.context)
            )
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

    fun notifyUserBookmark(position: Int) {
        (currentList[position] as? UserListItem.UserItem)
            ?.let { it.bookmarked = it.bookmarked.not() }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<UserListItem>() {

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
}
