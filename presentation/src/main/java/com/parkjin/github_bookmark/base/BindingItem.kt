package com.parkjin.github_bookmark.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.parkjin.github_bookmark.BR

/**
 * RecyclerView에서 공통적으로 사용하는 Item
 */
data class BindingItem(
    val viewModel: Any,
    val navigator: Any,
    @LayoutRes val layoutId: Int
) {
    fun bind(binding: ViewDataBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.navigator, navigator)
    }
}