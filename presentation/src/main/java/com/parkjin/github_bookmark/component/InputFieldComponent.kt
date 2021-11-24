package com.parkjin.github_bookmark.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.parkjin.github_bookmark.base.BaseComponent
import com.parkjin.github_bookmark.databinding.ComponentInputFieldBinding

class InputFieldComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseComponent(context, attrs, defStyleAttr) {

    private lateinit var binding: ComponentInputFieldBinding
    val viewModel: InputFieldViewModel by lazy {
        findViewTreeViewModelStoreOwner()
            ?.let { ViewModelProvider(it)[InputFieldViewModel::class.java] }
            ?: InputFieldViewModel()
    }

    override fun onCreate() {
        binding = ComponentInputFieldBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        if (::binding.isInitialized) binding.unbind()
    }
}
