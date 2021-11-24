package com.parkjin.github_bookmark.ui.github

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.parkjin.github_bookmark.BR
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingFragment
import com.parkjin.github_bookmark.base.EventObserver
import com.parkjin.github_bookmark.databinding.FragmentGithubBinding
import com.parkjin.github_bookmark.extension.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubFragment: BindingFragment<FragmentGithubBinding>() {

    private val viewModel: GithubViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_github

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun observeEvent() {
        with(viewModel) {
            onErrorEvent.observe(this@GithubFragment, EventObserver {
                showMessage(it.message)
            })
        }

        with(binding.inputField.viewModel) {
            onSearchEvent.observe(this@GithubFragment, EventObserver {
                viewModel.getUsersForName(it ?: return@EventObserver)
            })
        }
    }
}
