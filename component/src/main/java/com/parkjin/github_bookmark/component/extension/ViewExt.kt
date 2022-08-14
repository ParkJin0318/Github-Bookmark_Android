package com.parkjin.github_bookmark.component.extension

import android.view.View
import android.view.ViewGroup

fun <T : View> T.setLayoutParams(width: Int, height: Int) = also {
    this.layoutParams = ViewGroup.LayoutParams(width, height)
}
