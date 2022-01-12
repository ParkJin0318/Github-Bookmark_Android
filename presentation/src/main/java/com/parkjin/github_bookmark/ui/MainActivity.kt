package com.parkjin.github_bookmark.ui

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingActivity
import com.parkjin.github_bookmark.base.ViewPagerAdapter
import com.parkjin.github_bookmark.databinding.ActivityMainBinding
import com.parkjin.github_bookmark.model.UserType
import com.parkjin.github_bookmark.ui.user.UserListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BindingActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTabLayout()
    }

    private fun setTabLayout() {
        val fragments = listOf(
            UserListFragment.newInstance(UserListFragment.Argument(UserType.GITHUB)),
            UserListFragment.newInstance(UserListFragment.Argument(UserType.BOOKMARK))
        )

        val viewPagerAdapter = ViewPagerAdapter(this).apply {
            setFragmentList(fragments)
        }

        binding.pagerLayout.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pagerLayout) { tab, position ->
            tab.text = UserType.values()[position].title
        }.attach()
    }
}
