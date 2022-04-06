package com.parkjin.github_bookmark.presentation.ui.user

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.presentation.databinding.ViewUserHeaderItemBinding
import com.parkjin.github_bookmark.presentation.databinding.ViewUserItemBinding

sealed class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class UserHeaderViewHolder(
        private val binding: ViewUserHeaderItemBinding
    ) : UserListViewHolder(binding.root) {

        fun bind(item: UserListItem.UserHeader) {
            binding.header = item.header
        }
    }

    class UserItemViewHolder(
        private val binding: ViewUserItemBinding,
        private val listener: UserListAdapter.UserListener
    ) : UserListViewHolder(binding.root) {

        fun bind(item: UserListItem.UserItem) {
            binding.item = item
            binding.listener = listener
        }
    }

    class LoadingViewHolder(view: View) : UserListViewHolder(view)
}
