package com.parkjin.github_bookmark.component.header

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.parkjin.github_bookmark.component.R

class HeaderView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val txtHeader: AppCompatTextView

    var text: CharSequence = ""
        get() = txtHeader.text
        set(value) {
            field = value
            txtHeader.text = value
        }

    init {
        inflate(context, R.layout.view_header, this)
        txtHeader = findViewById(R.id.txt_header)
    }
}