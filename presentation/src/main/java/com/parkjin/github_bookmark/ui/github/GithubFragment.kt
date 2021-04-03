package com.parkjin.github_bookmark.ui.github

import android.os.Bundle
import android.view.View
import com.parkjin.github_bookmark.BR
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingFragment
import com.parkjin.github_bookmark.base.EventObserver
import com.parkjin.github_bookmark.databinding.FragmentGithubBinding
import com.parkjin.github_bookmark.extension.toast
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Github 사용자 목록을 표시하는 Fragment
 */
class GithubFragment: BindingFragment<FragmentGithubBinding>() {

    private val viewModel: GithubViewModel by lazy {
        getViewModel(GithubViewModel::class)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_github

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllSearchUser(viewModel.userName.value!!)
    }

    override fun observeEvent() {
        with(viewModel) {
            onErrorEvent.observe(this@GithubFragment, EventObserver {
                toast(it)
            })
        }
    }
}