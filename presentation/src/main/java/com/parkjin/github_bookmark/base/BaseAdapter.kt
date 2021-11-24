package com.parkjin.github_bookmark.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T: Any, VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private val _currentList: MutableList<T> = mutableListOf()
    val currentList: List<T>
        get() = _currentList

    override fun getItemCount(): Int  = currentList.size

    fun getItem(position: Int): T = _currentList[position]

    fun notifyItemChanged(item: T) {
        val index = currentList.indexOf(item)
        notifyItemChanged(index)
    }

    fun addItems(items: List<T>): Int {
        _currentList.addAll(items)
        return currentList.size
    }

    fun addItemsChanged(items: List<T>) {
        val positionStart = currentList.size
        val itemCount = addItems(items).dec()
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun addItem(item: T): Int {
        _currentList.add(item)
        return currentList.indexOf(item)
    }

    fun addItemChanged(item: T) {
        val index = addItem(item)
        notifyItemInserted(index)
    }

    fun removeItem(item: T): Int {
        val index = currentList.indexOf(item)
        _currentList.remove(item)
        return index
    }

    fun removeItemChanged(item: T) {
        val index = removeItem(item)
        notifyItemRemoved(index)
    }

    fun replaceItemsChanged(items: List<T>) {
        _currentList.clear()
        addItemsChanged(items)
    }

    fun replaceItem(oldItem: T, newItem: T): Int {
        val index = currentList.indexOf(oldItem)
        _currentList[index] = newItem
        return index
    }

    fun replaceItemChanged(oldItem: T, newItem: T) {
        val index = replaceItem(oldItem, newItem)
        notifyItemChanged(index)
    }
}
