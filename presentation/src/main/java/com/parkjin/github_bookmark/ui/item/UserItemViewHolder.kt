package com.parkjin.github_bookmark.ui.item

import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.databinding.ViewUserItemBinding
import com.parkjin.github_bookmark.model.User

class UserItemViewHolder(
    private val binding: ViewUserItemBinding,
    private val navigator: UserItemNavigator
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: User) {
        binding.model = item
        binding.navigator = navigator
    }
}