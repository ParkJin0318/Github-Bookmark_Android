package com.parkjin.github_bookmark.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingItem
import com.parkjin.github_bookmark.base.RecyclerViewAdapter

/**
 * BindingItem을 recyclerView에 연결해주는 BindingAdapter 속성
 */
@BindingAdapter("recyclerItems")
fun RecyclerView.setRecyclerViewItems(items: List<BindingItem>?) {
    if (adapter == null) {
        this.adapter = RecyclerViewAdapter()
        this.layoutManager = LinearLayoutManager(context)
    }
    items?.let { (adapter as? RecyclerViewAdapter)?.updateItem(it) }
}

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
@BindingAdapter("isBookmark")
fun ImageView.setBookmarkTint(isBookmark: Boolean?) {
    val colorYellow: Int = ContextCompat.getColor(context, R.color.yellow)
    val colorBlack: Int = ContextCompat.getColor(context, R.color.black)

    isBookmark?.let {
        if (it)
            this.setColorFilter(colorYellow)
        else
            this.setColorFilter(colorBlack)
    }
}