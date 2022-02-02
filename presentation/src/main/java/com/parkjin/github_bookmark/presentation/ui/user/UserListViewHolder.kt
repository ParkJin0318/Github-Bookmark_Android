package com.parkjin.github_bookmark.presentation.ui.user

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.presentation.databinding.ViewLoadingItemBinding
import com.parkjin.github_bookmark.presentation.databinding.ViewUserHeaderItemBinding
import com.parkjin.github_bookmark.presentation.databinding.ViewUserItemBinding

sealed class UserListViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    class UserHeaderViewHolder(
        private val binding: ViewUserHeaderItemBinding
    ) : UserListViewHolder(binding) {

        fun bind(item: UserListItem.UserHeader) {
            binding.header = item.header
        }
    }

    class UserItemViewHolder(
        private val binding: ViewUserItemBinding,
        private val listener: UserListAdapter.UserListener
    ) : UserListViewHolder(binding) {

        fun bind(item: UserListItem.UserItem) {
            binding.item = item
            binding.listener = listener
        }
    }

    class LoadingViewHolder(binding: ViewLoadingItemBinding) : UserListViewHolder(binding)
}
