package com.parkjin.github_bookmark.component.user

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.parkjin.github_bookmark.component.R
import com.parkjin.github_bookmark.component.extension.setLayoutParams

class UserItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val imgUser: AppCompatImageView
    private val txtName: AppCompatTextView
    private val imgStarred: AppCompatImageView

    var imgUrl: String? = null
        set(value) {
            field = value
            setUserImage(value)
        }

    var name: CharSequence = ""
        get() = txtName.text
        set(value) {
            field = value
            txtName.text = value
        }

    var starred: Boolean = false
        set(value) {
            field = value
            setStarredImage(value)
        }

    init {
        inflate(context, R.layout.view_user_item, this)
        setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        imgUser = findViewById(R.id.img_user)
        txtName = findViewById(R.id.txt_name)
        imgStarred = findViewById(R.id.img_starred)
    }

    fun setOnStarredClick(action: (Boolean) -> Unit) {
        imgStarred.setOnClickListener {
            starred = starred.not()
            action.invoke(starred)
        }
    }

    private fun setUserImage(imgUrl: String?) {
        if (imgUrl.isNullOrBlank()) return

        Glide.with(context)
            .load(imgUrl)
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .transform(CircleCrop())
            .into(imgUser)
    }

    private fun setStarredImage(starred: Boolean) {
        val color = ContextCompat.getColor(context, if (starred) R.color.blue else R.color.gray)
        imgStarred.imageTintList = ColorStateList.valueOf(color)
    }
}