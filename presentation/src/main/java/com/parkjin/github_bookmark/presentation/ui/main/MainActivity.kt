package com.parkjin.github_bookmark.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.parkjin.github_bookmark.presentation.R
import com.parkjin.github_bookmark.presentation.core.BindingActivity
import com.parkjin.github_bookmark.presentation.core.ViewPagerAdapter
import com.parkjin.github_bookmark.presentation.databinding.ActivityMainBinding
import com.parkjin.github_bookmark.presentation.ui.user.UserListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabLayout()
    }

    private fun initTabLayout() {
        binding.pagerLayout.adapter = ViewPagerAdapter(
            activity = this,
            fragments = listOf(
                UserListFragment.newInstance(MainTabType.GITHUB),
                UserListFragment.newInstance(MainTabType.BOOKMARK)
            )
        )

        TabLayoutMediator(binding.tabLayout, binding.pagerLayout) { tab, position ->
            tab.text = MainTabType.values()[position].title
        }.attach()
    }
}
