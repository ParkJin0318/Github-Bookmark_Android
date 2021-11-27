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
import com.parkjin.github_bookmark.extension.showMessage
import com.parkjin.github_bookmark.model.UserType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class UserFragment: BindingFragment<FragmentUserBinding>() {

    companion object {
        private const val ARGUMENT_KEY = "ARGUMENT_KEY"

        fun newInstance(argument: Argument) = UserFragment().apply {
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
            ?.let { viewModel.setUserType(it.type) }
    }

    override fun observeEvent() {
        with(viewModel) {
            onErrorEvent.observe(this@UserFragment, EventObserver {
                showMessage(it.message)
            })
        }

        with(binding.inputField.viewModel) {
            onSearchEvent.observe(this@UserFragment, EventObserver {
                viewModel.getUsers(it ?: return@EventObserver)
            })
        }
    }

    @Parcelize
    data class Argument(
        val type: UserType
    ) : Parcelable
}
