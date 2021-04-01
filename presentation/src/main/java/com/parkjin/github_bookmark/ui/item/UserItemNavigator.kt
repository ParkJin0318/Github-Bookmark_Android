package com.parkjin.github_bookmark.ui.item

import com.parkjin.github_bookmark.model.User

interface UserItemNavigator {
    fun onClickBookmark(user: User)
}