package com.parkjin.github_bookmark.ui

import android.os.Bundle
import com.parkjin.github_bookmark.BR
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingActivity
import com.parkjin.github_bookmark.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BindingActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by lazy {
        getViewModel(MainViewModel::class)
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun observeEvent() {
        with(viewModel) {

        }
    }
}