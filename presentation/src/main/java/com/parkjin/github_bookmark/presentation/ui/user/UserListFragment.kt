package com.parkjin.github_bookmark.presentation.ui.user

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.parkjin.github_bookmark.presentation.R
import com.parkjin.github_bookmark.presentation.core.BindingFragment
import com.parkjin.github_bookmark.presentation.databinding.FragmentUserBinding
import com.parkjin.github_bookmark.presentation.extension.showToast
import com.parkjin.github_bookmark.presentation.ui.main.MainTabType
import com.parkjin.github_bookmark.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BindingFragment<FragmentUserBinding>(R.layout.fragment_user) {

    companion object {
        fun newInstance(tabType: MainTabType) = UserListFragment().apply {
            this.arguments = bundleOf(
                MainTabType::class.java.name to tabType
            )
        }
    }

    private val viewModel: UserListViewModel by viewModels()
    private val shared: MainViewModel by activityViewModels()

    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.adapter = adapter
        binding.itemDecoration = UserListItemDecoration(view.context)
    }

    override fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.userListItems
                .collect { userListItems ->
                    adapter.submitList(userListItems)
                }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.onErrorEvent
                .collect { throwable ->
                    context?.showToast(throwable.message)
                }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.toggleBookmark
                .collect { (tabType, userItem) ->
                    shared.toggleBookmark(tabType, userItem)
                }
        }

        lifecycleScope.launchWhenStarted {
            shared.bookmarkedUser
                .collect { (tabType, userItem) ->
                    when (tabType) {
                        MainTabType.GITHUB -> viewModel.bookmarkedUserForGithubTab(userItem)
                        MainTabType.BOOKMARK -> viewModel.bookmarkedUserForBookmarkTab(userItem)
                    }
                }
        }
    }
}
