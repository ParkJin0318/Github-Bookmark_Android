package com.parkjin.github_bookmark.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Fragment Extension 함수
 */
fun Fragment.toast(message: String?) {
    Toast.makeText(context!!.applicationContext, message, Toast.LENGTH_SHORT).show()
}