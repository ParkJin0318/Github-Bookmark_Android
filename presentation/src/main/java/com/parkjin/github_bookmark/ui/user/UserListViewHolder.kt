package com.parkjin.github_bookmark.ui.user

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.databinding.ViewLoadingItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserHeaderItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserItemBinding

sealed class UserListViewHolder(
    open val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    class UserHeaderViewHolder(
        override val binding: ViewUserHeaderItemBinding
    ) : UserListViewHolder(binding) {

        fun bind(item: UserListItem.UserHeader) {
            binding.header = item.header
        }
    }

    class UserItemViewHolder(
        override val binding: ViewUserItemBinding,
        private val listener: UserListAdapter.UserListener
    ) : UserListViewHolder(binding) {

        fun bind(item: UserListItem.UserItem) {
            binding.item = item
            binding.listener = listener
        }
    }

    class LoadingViewHolder(binding: ViewLoadingItemBinding) : UserListViewHolder(binding)
}
