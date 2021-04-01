package com.parkjin.github_bookmark.ui.bookmark

import android.os.Bundle
import android.view.View
import com.parkjin.github_bookmark.BR
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingFragment
import com.parkjin.github_bookmark.databinding.FragmentBookmarkBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class BookmarkFragment: BindingFragment<FragmentBookmarkBinding>() {

    private val viewModel: BookmarkViewModel by lazy {
        getViewModel(BookmarkViewModel::class)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_bookmark

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun observeEvent() { }
}