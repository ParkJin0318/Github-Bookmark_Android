package com.parkjin.github_bookmark.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView에서 공통적으로 사용하는 ViewHolder
 */
class BindingViewHolder(
    val binding: ViewDataBinding
): RecyclerView.ViewHolder(binding.root)