package com.parkjin.github_bookmark.component.loading

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.parkjin.github_bookmark.component.R
import com.parkjin.github_bookmark.component.extension.setLayoutParams

class LoadingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val progress: ProgressBar

    init {
        inflate(context, R.layout.view_loading, this)
        setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        progress = findViewById(R.id.progress)
    }
}