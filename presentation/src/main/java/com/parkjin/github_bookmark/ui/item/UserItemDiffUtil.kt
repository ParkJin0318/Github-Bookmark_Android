package com.parkjin.github_bookmark.ui.item

import androidx.recyclerview.widget.DiffUtil
import com.parkjin.github_bookmark.model.User

class UserItemDiffUtil : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.equalsUser(newItem)

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.equalsUser(newItem)
}