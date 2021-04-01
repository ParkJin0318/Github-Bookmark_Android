package com.parkjin.github_bookmark.ui.bookmark

import androidx.lifecycle.MutableLiveData
import com.parkjin.github_bookmark.R
import com.parkjin.github_bookmark.base.BaseViewModel
import com.parkjin.github_bookmark.base.BindingItem
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.ui.item.UserItemViewModel
import com.parkjin.github_bookmark.usecase.AddBookMarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetAllBookmarkUserUseCase

class BookmarkViewModel(
        private val getAllBookmarkUserUseCase: GetAllBookmarkUserUseCase,
        private val addBookMarkUserUseCase: AddBookMarkUserUseCase
): BaseViewModel(), UserItemNavigator {
    val userName = MutableLiveData<String>("")
    val userItemList = MutableLiveData<ArrayList<BindingItem>>()

    val onErrorEvent = MutableLiveData<Event<String>>()
    val onBookmarkEvent = MutableLiveData<Event<Unit>>()

    val isLoading = MutableLiveData<Boolean>(true)

    fun getAllBookmarkUser(name: String?) {
        addDisposable(getAllBookmarkUserUseCase.execute(name ?: "")
            .subscribe({
                userItemList.value = ArrayList(it.toRecyclerItemList())
                isLoading.value = false
            }, {
                onErrorEvent.value = Event(it.message.toString())
            })
        )
    }

    private fun List<User>.toRecyclerItemList() = map { it.toViewModel() }

    private fun User.toViewModel() = UserItemViewModel(this).toRecyclerItem()

    private fun UserItemViewModel.toRecyclerItem() =
        BindingItem(
            viewModel = this,
            navigator = this@BookmarkViewModel,
            layoutId = R.layout.item_user
        )

    fun onClickSearch() {
        getAllBookmarkUser(userName.value)
    }

    override fun onClickBookmark(user: User) {
        addDisposable(addBookMarkUserUseCase.execute(user)
            .subscribe({
                onBookmarkEvent.value = Event(Unit)
            }, {
                onErrorEvent.value = Event(it.message.toString())
            }))
    }
}