package com.parkjin.github_bookmark.base

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

abstract class BaseComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun getLifecycle() = lifecycleRegistry

    abstract fun onCreate()

    abstract fun onDestroy()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleRegistry = LifecycleRegistry(this)
        onCreate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        onDestroy()
    }
}
