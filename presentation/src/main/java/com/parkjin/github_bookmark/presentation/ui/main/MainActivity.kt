package com.parkjin.github_bookmark.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.parkjin.github_bookmark.presentation.databinding.ActivityMainBinding
import com.parkjin.github_bookmark.presentation.ui.bookmark.BookmarkUserListFragment
import com.parkjin.github_bookmark.presentation.ui.github.GithubUserListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        binding.pagerLayout.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = MainTabType.values().size
            override fun createFragment(position: Int) = when (MainTabType.from(position)) {
                MainTabType.GITHUB -> GithubUserListFragment()
                MainTabType.BOOKMARK -> BookmarkUserListFragment()
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.pagerLayout) { tab, position ->
            tab.text = MainTabType.values()[position].title
        }.attach()
    }
}
