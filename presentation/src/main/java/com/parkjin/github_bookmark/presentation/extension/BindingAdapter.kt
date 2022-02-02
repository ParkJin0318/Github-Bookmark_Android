package com.parkjin.github_bookmark.presentation.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.parkjin.github_bookmark.presentation.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (url.isNullOrBlank().not()) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(this)
    }
}
