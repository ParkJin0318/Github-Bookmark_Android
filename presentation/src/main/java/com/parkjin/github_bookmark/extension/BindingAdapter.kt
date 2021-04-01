package com.parkjin.github_bookmark.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingItem
import com.parkjin.github_bookmark.base.RecyclerViewAdapter

@BindingAdapter("recyclerItems")
fun RecyclerView.setRecyclerViewItems(items: List<BindingItem>?) {
    if (adapter == null) {
        adapter = RecyclerViewAdapter()
        layoutManager = LinearLayoutManager(context)
    }
    items?.let {
        (adapter as? RecyclerViewAdapter)?.updateItem(it)
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (!url.isNullOrBlank()) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(this)
    }
}