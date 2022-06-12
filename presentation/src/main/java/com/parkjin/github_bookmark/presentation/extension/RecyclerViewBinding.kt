package com.parkjin.github_bookmark.presentation.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.presentation.ui.user.UserListAdapter
import com.parkjin.github_bookmark.presentation.ui.user.UserListItemDecoration

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter", "itemDecoration", "onScrolled")
    fun setList(
        view: RecyclerView,
        adapter: ListAdapter<*, *>?,
        itemDecoration: RecyclerView.ItemDecoration?,
        scrollListener: RecyclerView.OnScrollListener?
    ) {
        if (view.adapter == null && adapter != null && itemDecoration != null) {
            view.addItemDecoration(UserListItemDecoration(view.context))
            scrollListener?.let(view::addOnScrollListener)
            view.adapter = adapter
        }
    }
}
