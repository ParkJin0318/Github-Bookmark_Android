package com.parkjin.github_bookmark.ui.item

import com.parkjin.github_bookmark.model.User
import java.util.*

class UserItemViewModel(val item: User) {
    val header = item.name.toUpperCase(Locale.ROOT)
}