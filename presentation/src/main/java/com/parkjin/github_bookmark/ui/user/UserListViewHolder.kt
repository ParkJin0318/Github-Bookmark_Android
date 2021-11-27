package com.parkjin.github_bookmark.ui.user

import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.databinding.ViewLoadingItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserHeaderItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserItemBinding
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.user.UserAdapter

class UserHeaderViewHolder(
    private val binding: ViewUserHeaderItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(header: String) {
        binding.header = header
    }
}

class UserItemViewHolder(
    private val binding: ViewUserItemBinding,
    private val listener: UserAdapter.UserListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: User) {
        binding.model = item
        binding.listener = listener
    }
}

class LoadingViewHolder(
    private val binding: ViewLoadingItemBinding
) : RecyclerView.ViewHolder(binding.root)
