package com.parkjin.github_bookmark.component.input

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object InputFiledViewBinding {

    @JvmStatic
    @BindingAdapter("onSearchClick")
    fun setOnSearchClick(view: InputFieldView, action: () -> Unit) {
        view.imgSearch.setOnClickListener { action.invoke() }
    }

    @JvmStatic
    @BindingAdapter("text")
    fun setText(view: InputFieldView, content: String?) {
        val old = view.txtInput.text.toString()
        if (old != content) {
            view.txtInput.setText(content)
        }
    }

    @JvmStatic
    @BindingAdapter("textAttrChanged")
    fun setTextAttrChanged(view: InputFieldView, listener: InverseBindingListener?) {
        view.txtInput.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) { listener?.onChange() }
            }
        )
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "text", event = "textAttrChanged")
    fun getText(view: InputFieldView): String {
        return view.txtInput.text.toString()
    }
}
