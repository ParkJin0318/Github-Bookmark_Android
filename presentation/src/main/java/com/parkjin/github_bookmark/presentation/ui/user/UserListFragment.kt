package com.parkjin.github_bookmark.presentation.ui.user

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.parkjin.github_bookmark.domain.model.UserType
import com.parkjin.github_bookmark.presentation.BR
import com.parkjin.github_bookmark.presentation.R
import com.parkjin.github_bookmark.presentation.base.BindingFragment
import com.parkjin.github_bookmark.presentation.databinding.FragmentUserBinding
import com.parkjin.github_bookmark.presentation.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class UserListFragment : BindingFragment<FragmentUserBinding>() {

    companion object {
        private const val ARGUMENT_KEY = "ARGUMENT_KEY"

        fun newInstance(type: UserType) = UserListFragment().apply {
            this.arguments = bundleOf(
                ARGUMENT_KEY to Argument(type)
            )
        }
    }

    private val viewModel: UserViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_user

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = arguments?.getParcelable<Argument>(ARGUMENT_KEY) ?: return

        binding.setVariable(BR.viewModel, viewModel)
        viewModel.initUserType(argument.type)
    }

    override fun observeEvent() {
        with(viewModel) {
            onErrorEvent.observe(this@UserListFragment,
                com.parkjin.github_bookmark.presentation.base.EventObserver {
                    context?.showToast(it.message)
                })
        }
    }

    @Parcelize
    data class Argument(
        val type: UserType
    ) : Parcelable
}
