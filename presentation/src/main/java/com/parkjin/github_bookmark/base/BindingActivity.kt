package com.parkjin.github_bookmark.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<VB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: VB
        private set

    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected abstract fun observeEvent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        observeEvent()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::binding.isInitialized) binding.unbind()
    }
}
