package com.parkjin.github_bookmark.presentation.ui.user

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.presentation.extension.setLayoutParams
import com.parkjin.github_bookmark.component.header.HeaderView
import com.parkjin.github_bookmark.component.loading.LoadingView
import com.parkjin.github_bookmark.component.user.UserItemView

sealed class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class UserHeaderViewHolder(private val view: HeaderView) : UserListViewHolder(view) {

        init {
            view.setLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        fun bind(item: UserListItem.UserHeader) {
            view.text = item.header
        }
    }

    class UserItemViewHolder(
        private val view: UserItemView,
        private val onStarredClick: (String) -> Unit
    ) : UserListViewHolder(view) {

        init {
            view.setLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        fun bind(item: UserListItem.UserItem) {
            view.name = item.user.name
            view.imgUrl = item.user.profileImageUrl
            view.starred = item.bookmarked
            view.setOnStarredClick { onStarredClick(item.user.name) }
        }
    }

    class LoadingViewHolder(private val view: LoadingView) : UserListViewHolder(view) {

        init {
            view.setLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

}
