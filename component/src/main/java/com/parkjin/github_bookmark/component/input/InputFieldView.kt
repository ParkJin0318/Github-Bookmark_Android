package com.parkjin.github_bookmark.component.input

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.parkjin.github_bookmark.component.R

class InputFieldView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val txtInput: AppCompatEditText
    private val imgSearch: AppCompatImageView

    var text: CharSequence = ""
        get() = txtInput.text.toString()
        set(value) {
            field = value
            txtInput.setText(value)
        }

    init {
        inflate(context, R.layout.view_input_field, this)
        txtInput = findViewById(R.id.txt_input)
        imgSearch = findViewById(R.id.img_search)
        txtInput.setHint(R.string.input_field_hint)
    }

    fun onTextChanged(action: (Editable?) -> Unit) {
        txtInput.doAfterTextChanged(action::invoke)
    }

    fun onSearchClick(action: () -> Unit) {
        imgSearch.setOnClickListener { action.invoke() }
    }
}
