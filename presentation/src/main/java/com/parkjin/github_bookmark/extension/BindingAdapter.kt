package com.parkjin.github_bookmark.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.parkjin.github_bookmark.R

/**
 * image url을 Glide를 통해 바로 로딩해주는 BindingAdapter 속성
 */
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

/**
 * bookmark를 했는지 판단하여 imageView의 tint 색상을 정하는 BindingAdapter 속성
 */
@BindingAdapter("bookmark")
fun ImageView.setBookmarkTint(isBookmark: Boolean?) {
    val colorYellow: Int = ContextCompat.getColor(context, R.color.yellow)
    val colorBlack: Int = ContextCompat.getColor(context, R.color.black)

    isBookmark?.let {
        if (it) this.setColorFilter(colorYellow)
        else this.setColorFilter(colorBlack)
    }
}