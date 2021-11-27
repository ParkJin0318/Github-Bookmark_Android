package com.parkjin.github_bookmark.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BaseAdapter
import com.parkjin.github_bookmark.databinding.ViewLoadingItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserHeaderItemBinding
import com.parkjin.github_bookmark.databinding.ViewUserItemBinding

class UserAdapter(
    private val listener: UserListener
) : BaseAdapter<UserListUIModel, RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) = getItem(position).toViewType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (UserListType.get(viewType)) {
            UserListType.USER_HEADER -> {
                val binding: ViewUserHeaderItemBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.view_user_header_item,
                        parent,
                        false
                    )

                UserHeaderViewHolder(binding)
            }

            UserListType.USER_ITEM -> {
                val binding: ViewUserItemBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.view_user_item,
                        parent,
                        false
                    )

                UserItemViewHolder(binding, listener)
            }

            UserListType.LOADING -> {
                val binding: ViewLoadingItemBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.view_loading_item,
                        parent,
                        false
                    )

                LoadingViewHolder(binding)
            }
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is UserHeaderViewHolder -> {
                val model = item as UserListUIModel.UserHeader
                holder.bind(model.header)
            }
            is UserItemViewHolder -> {
                val model = item as UserListUIModel.UserItem
                holder.bind(model.user)
            }
        }
    }

    interface UserListener {
        fun onClickBookmark(name: String)
    }
}
