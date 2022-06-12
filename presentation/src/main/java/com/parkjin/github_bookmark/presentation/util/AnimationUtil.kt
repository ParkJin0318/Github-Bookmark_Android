package com.parkjin.github_bookmark.presentation.util

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

object AnimationUtil {

    fun View.setExpandAnimation(isExpand: Boolean) {
        val animation = if (isExpand) expandAnimation() else collapseAnimation()
        animation.duration = 300
        startAnimation(animation)
    }

    private fun View.expandAnimation(): Animation {
        measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST)
        )

        val targetHeight = measuredHeight

        return object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                transformation: Transformation?
            ) {
                if (interpolatedTime <= 0.1) {
                    visibility = View.VISIBLE
                } else {
                    layoutParams.height = (targetHeight * interpolatedTime).toInt()
                    requestLayout()
                }
            }
        }
    }

    private fun View.collapseAnimation(): Animation {
        val targetHeight = measuredHeight

        return object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation?
            ) {
                if (interpolatedTime >= 0.9) {
                    visibility = View.GONE
                } else {
                    layoutParams.height = targetHeight - (targetHeight * interpolatedTime).toInt()
                    requestLayout()
                }
            }
        }
    }

}
