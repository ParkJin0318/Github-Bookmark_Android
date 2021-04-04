package com.parkjin.github_bookmark.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 공통적으로 사용하는 RecyclerViewAdapter,
 * BindingItem, BindingViewHolder를 사용하여 여러 아이템을 하나의 Adapter로 공유
 */
class RecyclerViewAdapter: RecyclerView.Adapter<BindingViewHolder>() {

    private val itemList = mutableListOf<BindingItem>()

    fun updateItem(newItems: List<BindingItem>) {
        this.itemList.clear()
        this.itemList.addAll(newItems)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): BindingItem {
        return itemList[position]
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).bind(holder.binding)
        holder.binding.executePendingBindings()
    }
}