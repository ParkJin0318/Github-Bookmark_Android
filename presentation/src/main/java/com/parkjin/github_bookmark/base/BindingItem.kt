package com.parkjin.github_bookmark.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

data class BindingItem(
    val viewModel: Any,
    val navigator: Any,
    @LayoutRes val layoutId: Int
) {
    fun bind(binding: ViewDataBinding) {
        // binding.setVariable(BR.viewModel, viewModel)
        // binding.setVariable(BR.navigator, navigator)
    }
}