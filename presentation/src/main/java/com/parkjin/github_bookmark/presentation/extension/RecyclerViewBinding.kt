package com.parkjin.github_bookmark.presentation.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.presentation.ui.user.UserListAdapter
import com.parkjin.github_bookmark.presentation.ui.user.UserListItemDecoration

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter", "itemDecoration")
    fun setList(
        view: RecyclerView,
        adapter: ListAdapter<*, *>?,
        itemDecoration: RecyclerView.ItemDecoration?
    ) {
        if (view.adapter == null && adapter != null && itemDecoration != null) {
            view.addItemDecoration(UserListItemDecoration(view.context))
            view.adapter = adapter
        }
    }
}
