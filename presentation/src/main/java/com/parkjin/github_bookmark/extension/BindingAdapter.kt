package com.parkjin.github_bookmark.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.parkjin.github_bookmark.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (!url.isNullOrBlank()) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(this)
    }
}
