package com.parkjin.github_bookmark.presentation.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.parkjin.github_bookmark.presentation.R
import com.parkjin.github_bookmark.presentation.ui.user.UserListAdapter
import com.parkjin.github_bookmark.presentation.ui.user.UserListItemDecoration

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (url.isNullOrBlank().not()) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .transform(CircleCrop())
            .into(this)
    }
}

@BindingAdapter("userAdapter")
fun RecyclerView.setUserAdapter(adapter: UserListAdapter?) {
    if (adapter != null && this.adapter == null) {
        addItemDecoration(UserListItemDecoration(context))
        layoutManager = LinearLayoutManager(context)
        this.adapter = adapter
    }
}
