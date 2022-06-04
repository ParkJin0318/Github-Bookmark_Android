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
        private val view: UserItemView
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
            view.starred = item.user.bookmarked
            view.setOnStarredClick {
                val newUser = item.user.copy(bookmarked = it)
                item.toggleBookmark(item.copy(user = newUser))
            }
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
