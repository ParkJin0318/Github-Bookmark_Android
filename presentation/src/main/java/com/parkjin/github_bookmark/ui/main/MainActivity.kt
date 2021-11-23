package com.parkjin.github_bookmark.ui.main

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingActivity
import com.parkjin.github_bookmark.base.ViewPagerAdapter
import com.parkjin.github_bookmark.databinding.ActivityMainBinding
import com.parkjin.github_bookmark.ui.bookmark.BookmarkFragment
import com.parkjin.github_bookmark.ui.github.GithubFragment
import dagger.hilt.android.AndroidEntryPoint

enum class TabType(val title: String) {
    GITHUB("Github"),
    BOOKMARK("Bookmark");
}

@AndroidEntryPoint
class MainActivity: BindingActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTabLayout()
    }

    private fun setTabLayout() {
        val fragments = listOf(
            GithubFragment(),
            BookmarkFragment()
        )

        val viewPagerAdapter = ViewPagerAdapter(this).apply {
            setFragmentList(fragments)
        }

        binding.pagerLayout.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pagerLayout) { tab, position ->
            tab.text = TabType.values()[position].title
            // binding.pagerLayout.setCurrentItem(tab.position, true)
        }.attach()
    }

    override fun observeEvent() { }
}
