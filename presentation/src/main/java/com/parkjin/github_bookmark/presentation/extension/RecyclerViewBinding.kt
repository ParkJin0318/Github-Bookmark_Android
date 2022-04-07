package com.parkjin.github_bookmark.presentation.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.presentation.ui.user.UserListAdapter
import com.parkjin.github_bookmark.presentation.ui.user.UserListItemDecoration

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("userAdapter")
    fun setUserAdapter(view: RecyclerView, adapter: UserListAdapter?) {
        if (view.adapter == null && adapter != null) {
            view.addItemDecoration(UserListItemDecoration(view.context))
            view.layoutManager = LinearLayoutManager(view.context)
            view.adapter = adapter
        }
    }
}
