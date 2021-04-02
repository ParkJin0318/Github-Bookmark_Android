package com.parkjin.github_bookmark.extension

import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BindingItem
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.ui.item.UserItemViewModel
import java.util.*

/**
 * User 객체 Extension 함수
 */
fun List<User>.toRecyclerItemList(navigator: UserItemNavigator) = map { it.toViewModel(navigator) }

fun User.toViewModel(navigator: UserItemNavigator) = UserItemViewModel(this).toRecyclerItem(navigator)

fun UserItemViewModel.toRecyclerItem(navigator: UserItemNavigator) =
        BindingItem(
                viewModel = this,
                navigator = navigator,
                layoutId = R.layout.item_user
        )

fun List<User>.headerSort(): List<User> {
    val userList = ArrayList<User>()

    val linkedList = LinkedList(this.sortedBy { user ->
        user.name.toUpperCase(Locale.ROOT)
    })

    while (!linkedList.isEmpty()) {
        val item = linkedList.remove().apply {
            this.isHeaderShow = true
        }
        userList.add(item)

        while (linkedList.peek() != null &&
                item.nameFirst() == linkedList.peek().nameFirst()) {
            userList.add(linkedList.remove())
        }
    }
    return userList
}