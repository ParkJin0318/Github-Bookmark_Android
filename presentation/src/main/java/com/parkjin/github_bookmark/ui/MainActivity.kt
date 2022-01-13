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
        initTabLayout()
    }

    private fun initTabLayout() {
        binding.pagerLayout.adapter = ViewPagerAdapter(this)
            .apply {
                setFragmentList(
                    listOf(
                        UserListFragment.newInstance(UserType.GITHUB),
                        UserListFragment.newInstance(UserType.BOOKMARK)
                    )
                )
            }

        TabLayoutMediator(binding.tabLayout, binding.pagerLayout) { tab, position ->
            tab.text = UserType.values()[position].title
        }.attach()
    }
}
