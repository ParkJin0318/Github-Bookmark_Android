package com.parkjin.github_bookmark.component.input

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.parkjin.github_bookmark.component.R
import com.parkjin.github_bookmark.component.extension.setLayoutParams

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
        setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        txtInput = findViewById(R.id.txt_input)
        imgSearch = findViewById(R.id.img_search)
        txtInput.setHint(R.string.input_field_hint)
    }

    fun onSearchClick(action: (String) -> Unit) {
        imgSearch.setOnClickListener { action.invoke(text.toString()) }
    }
}
