package com.parkjin.github_bookmark.presentation.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.parkjin.github_bookmark.component.header.HeaderView
import com.parkjin.github_bookmark.component.loading.LoadingView
import com.parkjin.github_bookmark.component.user.UserItemView
import com.parkjin.github_bookmark.presentation.R

class UserListAdapter : ListAdapter<UserListModel, UserListViewHolder>(DiffItemCallback()) {

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is UserListModel.Header -> R.layout.view_header
        is UserListModel.Item -> R.layout.view_user_item
        is UserListModel.Loading -> R.layout.view_loading
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
                holder.bind(item as UserListModel.Header)
            is UserListViewHolder.UserItemViewHolder ->
                holder.bind(item as UserListModel.Item)
            else -> return
        }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<UserListModel>() {

        override fun areItemsTheSame(oldItem: UserListModel, newItem: UserListModel): Boolean {
            val isSameHeader = oldItem is UserListModel.Header
                    && newItem is UserListModel.Header
                    && oldItem.header == newItem.header

            val isSameUser = oldItem is UserListModel.Item
                    && newItem is UserListModel.Item
                    && oldItem.user.name == newItem.user.name
                    && oldItem.user.bookmarked == newItem.user.bookmarked

            val isSameLoading = oldItem is UserListModel.Loading
                    && newItem is UserListModel.Loading
                    && oldItem == newItem

            return isSameHeader || isSameUser || isSameLoading
        }

        override fun areContentsTheSame(
            oldItem: UserListModel,
            newItem: UserListModel
        ) = oldItem == newItem
    }
}
