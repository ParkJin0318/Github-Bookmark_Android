package com.parkjin.github_bookmark.presentation.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.component.header.HeaderView
import com.parkjin.github_bookmark.component.loading.LoadingView
import com.parkjin.github_bookmark.component.user.UserItemView

sealed class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class UserHeaderViewHolder(private val view: HeaderView) : UserListViewHolder(view) {

        fun bind(item: UserListModel.Header) {
            view.text = item.header
        }
    }

    class UserItemViewHolder(
        private val view: UserItemView
    ) : UserListViewHolder(view) {

        fun bind(item: UserListModel.Item) {
            view.name = item.user.name
            view.imgUrl = item.user.profileImageUrl
            view.starred = item.user.bookmarked

            view.setOnStarredClick {
                val newUser = item.user.copy(bookmarked = it)
                item.toggleBookmark(item.copy(user = newUser))
            }
        }
    }

    class LoadingViewHolder(
        private val view: LoadingView
    ) : UserListViewHolder(view)

}
