package com.parkjin.github_bookmark.ui.item

import com.parkjin.github_bookmark.model.User
import java.util.*

/**
 * Item User의 ViewModel
 */
class UserItemViewModel(val item: User) {
    val header = item.name.toUpperCase(Locale.ROOT)
}