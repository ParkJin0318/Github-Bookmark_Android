package com.parkjin.github_bookmark.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.databinding.ViewUserItemBinding
import com.parkjin.github_bookmark.model.User

class UserAdapter(
    private val navigator: UserItemNavigator
) : ListAdapter<User, UserItemViewHolder>(UserItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val binding: ViewUserItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.view_user_item,
                parent,
                false
            )
        return UserItemViewHolder(binding, navigator)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}