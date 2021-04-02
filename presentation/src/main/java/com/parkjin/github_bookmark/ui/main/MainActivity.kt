package com.parkjin.github_bookmark.ui.main

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.parkjin.github_bookmark.BR
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingActivity
import com.parkjin.github_bookmark.base.ViewPagerAdapter
import com.parkjin.github_bookmark.databinding.ActivityMainBinding
import com.parkjin.github_bookmark.ui.bookmark.BookmarkFragment
import com.parkjin.github_bookmark.ui.github.GithubFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * ViewPager, TabBar를 관리하는 MainActivity
 */
class MainActivity : BindingActivity<ActivityMainBinding>() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.setFragmentList(arrayListOf(GithubFragment(), BookmarkFragment()))

        binding.pagerLayout.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pagerLayout) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.menu_github)
                1 -> tab.text = getString(R.string.menu_bookmark)
            }
            binding.pagerLayout.setCurrentItem(tab.position, true)
        }.attach()
    }

    override fun observeEvent() { }
}