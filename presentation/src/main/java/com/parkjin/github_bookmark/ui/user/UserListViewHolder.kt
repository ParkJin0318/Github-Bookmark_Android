package com.parkjin.github_bookmark.ui.user

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.databinding.ViewLoadingItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserHeaderItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserItemBinding
import com.parkjin.github_bookmark.model.User

sealed class UserListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

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

    class LoadingViewHolder(
        private val binding: ViewLoadingItemBinding
    ) : UserListViewHolder(binding.root)

}
