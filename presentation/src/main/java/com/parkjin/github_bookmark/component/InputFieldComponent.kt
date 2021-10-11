package com.parkjin.github_bookmark.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.databinding.ComponentInputFieldBinding
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.experimental.property.inject

final class InputFieldComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), KoinComponent {

    private val viewModel: InputFieldViewModel by inject()
    private lateinit var binding: ComponentInputFieldBinding

    val inputEditView: AppCompatEditText
    val searchImgView: AppCompatImageView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding = ComponentInputFieldBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    init {
        inflate(context, R.layout.component_input_field, this)

        inputEditView = findViewById(R.id.edit_input)
        searchImgView = findViewById(R.id.img_search)
    }
}