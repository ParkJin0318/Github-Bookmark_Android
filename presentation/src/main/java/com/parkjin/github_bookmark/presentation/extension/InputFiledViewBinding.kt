package com.parkjin.github_bookmark.presentation.extension

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.parkjin.github_bookmark.component.input.InputFieldView

object InputFiledViewBinding {

    @JvmStatic
    @BindingAdapter("onSearchClick")
    fun setOnSearchClick(view: InputFieldView, action: () -> Unit) {
        view.onSearchClick(action::invoke)
    }

    @JvmStatic
    @BindingAdapter("text")
    fun setText(view: InputFieldView, text: String?) {
        text?.let { if (view.text != it) view.text = it }
    }

    @JvmStatic
    @BindingAdapter("textAttrChanged")
    fun setTextAttrChanged(view: InputFieldView, listener: InverseBindingListener?) {
        view.onTextChanged { listener?.onChange() }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "text", event = "textAttrChanged")
    fun getText(view: InputFieldView): String = view.text.toString()
}
