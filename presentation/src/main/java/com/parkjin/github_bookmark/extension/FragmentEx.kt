package com.parkjin.github_bookmark.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showMessage(message: String?) {
    Toast.makeText(context!!.applicationContext, message, Toast.LENGTH_SHORT).show()
}
