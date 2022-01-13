package com.parkjin.github_bookmark.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.databinding.ViewLoadingItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserHeaderItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserItemBinding
import com.parkjin.github_bookmark.model.User

class UserListAdapter(
    private val listener: UserListener
) : ListAdapter<UserListItem, UserListViewHolder>(UserListDiffCallback()) {

    override fun getItemViewType(position: Int) = getItem(position).type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder =
        when (UserListType.from(viewType)) {
            UserListType.USER_HEADER -> {
                val binding: ViewUserHeaderItemBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.view_user_header_item,
                        parent,
                        false
                    )

                UserListViewHolder.UserHeaderViewHolder(binding)
            }

            UserListType.USER_ITEM -> {
                val binding: ViewUserItemBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.view_user_item,
                        parent,
                        false
                    )

                UserListViewHolder.UserItemViewHolder(binding, listener)
            }

            UserListType.LOADING -> {
                val binding: ViewLoadingItemBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.view_loading_item,
                        parent,
                        false
                    )

                UserListViewHolder.LoadingViewHolder(binding)
            }
        }


    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is UserListViewHolder.UserHeaderViewHolder ->
                holder.bind(item as UserListItem.UserHeader)

            is UserListViewHolder.UserItemViewHolder ->
                holder.bind(item as UserListItem.UserItem)
        }
    }

    fun notifyUserBookmark(position: Int) {
        (currentList[position] as? UserListItem.UserItem)
            ?.let { it.bookmarked = it.bookmarked.not() }
    }

    interface UserListener {
        fun onClickBookmark(name: String)
    }
}