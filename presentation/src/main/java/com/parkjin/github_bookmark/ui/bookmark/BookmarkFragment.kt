package com.parkjin.github_bookmark.ui.bookmark

import android.os.Bundle
import android.view.View
import com.parkjin.github_bookmark.BR
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingFragment
import com.parkjin.github_bookmark.base.EventObserver
import com.parkjin.github_bookmark.databinding.FragmentBookmarkBinding
import com.parkjin.github_bookmark.extension.toast
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Bookmark 사용자 목록 표시하는 Fragment
 */
class BookmarkFragment: BindingFragment<FragmentBookmarkBinding>() {

    private val viewModel: BookmarkViewModel by lazy {
        getViewModel(BookmarkViewModel::class)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_bookmark

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllBookmarkUser(viewModel.userName.value)
    }

    override fun observeEvent() {
        with(viewModel) {
            onErrorEvent.observe(this@BookmarkFragment, EventObserver {
                toast(it)
            })
        }
    }
}