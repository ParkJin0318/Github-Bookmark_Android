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
        is UserListModel.HeaderModel -> R.layout.view_header
        is UserListModel.UserModel -> R.layout.view_user_item
        else -> R.layout.view_loading
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
                holder.bind(item as UserListModel.HeaderModel)
            is UserListViewHolder.UserItemViewHolder ->
                holder.bind(item as UserListModel.UserModel)
            else -> return
        }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<UserListModel>() {

        override fun areItemsTheSame(oldItem: UserListModel, newItem: UserListModel): Boolean {
            val isSameHeader = oldItem is UserListModel.HeaderModel &&
                newItem is UserListModel.HeaderModel &&
                oldItem.value == newItem.value

            val isSameUser = oldItem is UserListModel.UserModel &&
                newItem is UserListModel.UserModel &&
                oldItem.user == newItem.user

            val isSameLoading = oldItem is UserListModel.LoadingModel &&
                newItem is UserListModel.LoadingModel &&
                oldItem == newItem

            return isSameHeader || isSameUser || isSameLoading
        }

        override fun areContentsTheSame(
            oldItem: UserListModel,
            newItem: UserListModel
        ): Boolean {
            val isSameHeader = oldItem is UserListModel.HeaderModel &&
                newItem is UserListModel.HeaderModel &&
                oldItem.value == newItem.value

            val isSameUser = oldItem is UserListModel.UserModel &&
                newItem is UserListModel.UserModel &&
                oldItem.user == newItem.user

            val isSameLoading = oldItem is UserListModel.LoadingModel &&
                newItem is UserListModel.LoadingModel &&
                oldItem == newItem

            return isSameHeader || isSameUser || isSameLoading
        }
    }
}
