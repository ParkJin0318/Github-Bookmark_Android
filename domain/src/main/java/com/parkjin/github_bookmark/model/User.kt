package com.parkjin.github_bookmark.model

import java.util.LinkedList
import kotlin.collections.ArrayList

data class User(
    val name: String,
    val profileImageUrl: String
) {
    val upperName: String = name.uppercase()
    val firstName: Char = name.first().uppercaseChar()

    private var _isBookmark: Boolean = false
    val isBookmark: Boolean
        get() = _isBookmark

    fun bookmarked(isBookmark: Boolean): User {
        _isBookmark = isBookmark
        return this
    }

    private var _isShowHeader: Boolean = false
    val isShowHeader: Boolean
        get() = _isShowHeader

    fun showHeader(isShowHeader: Boolean): User {
        _isShowHeader = isShowHeader
        return this
    }

    fun equalsUser(newUser: User): Boolean = this.string() == newUser.string()

    private fun string(): String = name + isBookmark
}

fun List<User>.sort(): List<User> {
    val newUsers: ArrayList<User> = arrayListOf()

    val linkedList = LinkedList(this.sortedBy { it.upperName })

    while (!linkedList.isEmpty()) {
        val item: User = linkedList.remove()

        newUsers.add(item.showHeader(true))

        while (linkedList.peek() != null && item.firstName == linkedList.peek()?.firstName) {
            newUsers.add(linkedList.remove())
        }
    }

    return newUsers
}
