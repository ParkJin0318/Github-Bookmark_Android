package com.parkjin.github_bookmark.ui.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkjin.github_bookmark.BR
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingFragment
import com.parkjin.github_bookmark.databinding.FragmentGithubBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class GithubFragment: BindingFragment<FragmentGithubBinding>() {

    private val viewModel: GithubViewModel by lazy {
        getViewModel(GithubViewModel::class)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_github

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun observeEvent() { }
}