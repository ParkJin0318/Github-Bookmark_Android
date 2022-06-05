package com.parkjin.github_bookmark.presentation.ui.user

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.parkjin.github_bookmark.presentation.R
import com.parkjin.github_bookmark.presentation.core.BindingFragment
import com.parkjin.github_bookmark.presentation.databinding.FragmentUserBinding
import com.parkjin.github_bookmark.presentation.extension.showToast
import com.parkjin.github_bookmark.presentation.ui.main.MainTabType
import dagger.hilt.android.AndroidEntryPoint
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

    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = arguments?.getParcelable<Argument>(ARGUMENT_KEY) ?: return

        binding.viewModel = viewModel
        binding.adapter = adapter

        viewModel.initUserType(argument.type)
    }

    override fun observeLiveData() {
        lifecycleScope.launchWhenStarted {
            viewModel.userListItems
                .collect {
                    adapter.submitList(it)
                }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.onErrorEvent
                .collect {
                    context?.showToast(it.message)
                }
        }
    }

    @Parcelize
    data class Argument(
        val type: MainTabType
    ) : Parcelable
}
