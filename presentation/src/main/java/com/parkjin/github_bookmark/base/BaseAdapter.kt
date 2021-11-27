package com.parkjin.github_bookmark.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T: Any, VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private val _currentList: MutableList<T> = mutableListOf()
    val currentList: List<T>
        get() = _currentList

    override fun getItemCount(): Int  = currentList.size

    fun getItem(position: Int): T = _currentList[position]

    /**
     * notify
     */
    fun notifyItemChanged(item: T) {
        val index = currentList.indexOf(item)
        notifyItemChanged(index)
    }

    fun notifyItemsChanged(items: List<T>) {
        _currentList.clear()
        val itemCount = addItems(items).dec()
        notifyItemRangeChanged(0, itemCount)
    }

    /**
     * addAll
     */
    fun addItems(items: List<T>): Int {
        _currentList.addAll(items)
        return currentList.size
    }

    fun addItemsChanged(items: List<T>) {
        val positionStart = currentList.size
        val itemCount = addItems(items).dec()
        notifyItemRangeInserted(positionStart, itemCount)
    }

    /**
     * add
     */
    fun addItem(item: T): Int {
        _currentList.add(item)
        return currentList.indexOf(item)
    }

    fun addItem(frontItem: T, item: T): Int {
        val index = currentList.indexOf(frontItem).inc()
        _currentList.add(index, item)
        return currentList.indexOf(item)
    }

    fun addItemChanged(item: T) {
        val index = addItem(item)
        notifyItemInserted(index)
    }

    fun addItemChanged(frontItem: T, item: T) {
        val index = addItem(frontItem, item)
        notifyItemInserted(index)
    }

    /**
     * removeAll
     */
    fun removeItems(items: List<T>): Int {
        _currentList.removeAll(items)
        return currentList.size
    }

    fun removeItemsChanged(items: List<T>) {
        val positionStart = currentList.size
        val itemCount = removeItems(items).dec()
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    /**
     * remove
     */
    fun removeItem(item: T): Int {
        val index = currentList.indexOf(item)
        _currentList.remove(item)
        return index
    }

    fun removeItemChanged(item: T) {
        val index = removeItem(item)
        notifyItemRemoved(index)
    }

    /**
     * replace
     */
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
