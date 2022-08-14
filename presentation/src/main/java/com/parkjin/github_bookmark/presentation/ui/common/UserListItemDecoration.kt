package com.parkjin.github_bookmark.presentation.ui.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.presentation.R

class UserListItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val spacing4 = context.resources.getDimensionPixelSize(R.dimen.spacing4)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(spacing4, spacing4, spacing4, spacing4)
    }
}