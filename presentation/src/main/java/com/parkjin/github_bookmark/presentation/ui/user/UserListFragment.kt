package com.parkjin.github_bookmark.presentation.ui.user

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parkjin.github_bookmark.presentation.R
import com.parkjin.github_bookmark.presentation.core.BindingFragment
import com.parkjin.github_bookmark.presentation.databinding.FragmentUserBinding
import com.parkjin.github_bookmark.presentation.extension.showToast
import com.parkjin.github_bookmark.presentation.ui.main.MainTabType
import com.parkjin.github_bookmark.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class UserListFragment : BindingFragment<FragmentUserBinding>(R.layout.fragment_user) {

    companion object {
        private const val ARGUMENT_KEY = "ARGUMENT_KEY"

        fun newInstance(tabType: MainTabType) = UserListFragment().apply {
            this.arguments = bundleOf(
                ARGUMENT_KEY to Argument(tabType)
            )
        }
    }

    private val viewModel: UserListViewModel by viewModels()
    private val shared: MainViewModel by activityViewModels()

    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = arguments?.getParcelable<Argument>(ARGUMENT_KEY) ?: return

        binding.viewModel = viewModel

        val layoutManager = LinearLayoutManager(view.context)

        binding.list.addItemDecoration(UserListItemDecoration(view.context))
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.onChangedScrollY(dy)
            }
        })

        viewModel.setTabType(argument.type)
    }

    override fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.isVisibleInput
                .debounce(350)
                .collect {
                    if (it) {
                        val animation = TranslateAnimation(0f, 0f, 15f, 0f)
                        animation.duration = 150
                        binding.inputField.isVisible = true
                        binding.inputField.animation = animation
                    } else {
                        val animation = TranslateAnimation(0f, 0f, 0f, 15f)
                        animation.duration = 150
                        binding.inputField.isVisible = false
                        binding.inputField.animation = animation
                    }

                }
        }

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

    @Parcelize
    data class Argument(val type: MainTabType) : Parcelable
}
