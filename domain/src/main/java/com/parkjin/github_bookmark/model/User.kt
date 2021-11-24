package com.parkjin.github_bookmark.model

import java.util.LinkedList
import kotlin.collections.ArrayList

data class User(
    val name: String,
    val profileImageUrl: String,
    val bookmarked: Boolean = false,
    val showHeader: Boolean = false
)

val User.upperName: String
    get() = name.uppercase()

val User.firstName: Char
    get() = name.first().uppercaseChar()

fun List<User>.sort(): List<User> {
    val newUsers: ArrayList<User> = arrayListOf()

    val linkedList = LinkedList(this.sortedBy { it.upperName })

    while (!linkedList.isEmpty()) {
        val item: User = linkedList.remove()

        newUsers.add(item.copy(showHeader = true))

        while (linkedList.peek() != null && item.firstName == linkedList.peek()?.firstName) {
            newUsers.add(linkedList.remove())
        }
    }

    return newUsers
}
