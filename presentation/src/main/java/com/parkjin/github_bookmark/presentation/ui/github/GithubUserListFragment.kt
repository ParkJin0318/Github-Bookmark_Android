package com.parkjin.github_bookmark.presentation.ui.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.parkjin.github_bookmark.presentation.databinding.FragmentUserListBinding
import com.parkjin.github_bookmark.presentation.extension.showToast
import com.parkjin.github_bookmark.presentation.ui.common.UserListAdapter
import com.parkjin.github_bookmark.presentation.ui.common.UserListItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class GithubUserListFragment : Fragment() {

    private val viewModel by viewModels<GithubUserListViewModel>()

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserListBinding.inflate(
        inflater,
        container,
        false
    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        observeState()
    }

    private fun initializeView() {
        binding.list.adapter = UserListAdapter().also { adapter = it }
        binding.list.addItemDecoration(UserListItemDecoration(requireContext()))

        binding.inputField.onSearchAction {
            viewModel.setAction(GithubUserListViewModel.Action.SearchUserList(it))
        }
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state
                .map { it.userListModels }
                .collect {
                    adapter.submitList(it)
                }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state
                .map { it.errorMessage }
                .filter { it != null }
                .collect {
                    context?.showToast(it)
                }
        }
    }
}