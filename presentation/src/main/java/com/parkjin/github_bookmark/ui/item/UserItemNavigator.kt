package com.parkjin.github_bookmark.ui.item

import com.parkjin.github_bookmark.model.User

/**
 * Item의 이벤트 처리를 하는 ItemNavigator
 */
interface UserItemNavigator {
    fun onClickBookmark(user: User)
}