package com.parkjin.github_bookmark.component.input

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.parkjin.github_bookmark.component.R
import com.parkjin.github_bookmark.component.extension.setLayoutParams

class InputFieldView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attributeSet, defStyle) {

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

        setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_gray))
        radius = resources.getDimensionPixelSize(R.dimen.spacing8).toFloat()
        cardElevation = 0f

        txtInput = findViewById(R.id.txt_input)
        imgSearch = findViewById(R.id.img_search)
        txtInput.setHint(R.string.input_field_hint)
    }

    fun onSearchAction(action: (String) -> Unit) {
        txtInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                action.invoke(text.toString())
            }
            false
        }
    }
}
