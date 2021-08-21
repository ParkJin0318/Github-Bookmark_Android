package com.parkjin.github_bookmark.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 공통적으로 사용하는 ViewPagerAdapter
 */
class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragmentList = mutableListOf<Fragment>()

    fun setFragmentList(fragments: ArrayList<Fragment>) {
        fragmentList.clear()
        fragmentList.addAll(fragments)
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}