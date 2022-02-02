package com.parkjin.github_bookmark.presentation.ui

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.parkjin.github_bookmark.domain.model.UserType
import com.parkjin.github_bookmark.presentation.R
import com.parkjin.github_bookmark.presentation.base.BindingActivity
import com.parkjin.github_bookmark.presentation.databinding.ActivityMainBinding
import com.parkjin.github_bookmark.presentation.ui.user.UserListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BindingActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabLayout()
    }

    private fun initTabLayout() {
        binding.pagerLayout.adapter =
            com.parkjin.github_bookmark.presentation.base.ViewPagerAdapter(
                this,
                listOf(
                    UserListFragment.newInstance(UserType.GITHUB),
                    UserListFragment.newInstance(UserType.BOOKMARK)
                )
            )

        TabLayoutMediator(binding.tabLayout, binding.pagerLayout) { tab, position ->
            tab.text = UserType.values()[position].title
        }.attach()
    }
}
