package com.parkjin.github_bookmark.ui.user

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.parkjin.github_bookmark.BR
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingFragment
import com.parkjin.github_bookmark.base.EventObserver
import com.parkjin.github_bookmark.databinding.FragmentUserBinding
import com.parkjin.github_bookmark.extension.showToast
import com.parkjin.github_bookmark.model.UserType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class UserListFragment: BindingFragment<FragmentUserBinding>() {

    companion object {
        private const val ARGUMENT_KEY = "ARGUMENT_KEY"

        fun newInstance(argument: Argument) = UserListFragment().apply {
            this.arguments = bundleOf(
                ARGUMENT_KEY to argument
            )
        }
    }

    private val viewModel: UserViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_user

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)

        arguments?.getParcelable<Argument>(ARGUMENT_KEY)
            ?.let { viewModel.initUserType(it.type) }
    }

    override fun observeEvent() {
        with(viewModel) {
            onErrorEvent.observe(this@UserListFragment, EventObserver {
                context?.showToast(it.message)
            })
        }
    }

    @Parcelize
    data class Argument(
        val type: UserType
    ) : Parcelable
}