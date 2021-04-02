package com.parkjin.github_bookmark.ui.github

import androidx.lifecycle.MutableLiveData
import com.parkjin.github_bookmark.base.BaseViewModel
import com.parkjin.github_bookmark.base.BindingItem
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.extension.headerSort
import com.parkjin.github_bookmark.extension.toRecyclerItemList
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.usecase.AddBookMarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetAllSearchUserUseCase
import kotlin.collections.ArrayList

class GithubViewModel(
    private val getAllSearchUserUseCase: GetAllSearchUserUseCase,
    private val addBookMarkUserUseCase: AddBookMarkUserUseCase
): BaseViewModel(), UserItemNavigator {
    val userName = MutableLiveData<String>("")
    val userItemList = MutableLiveData<ArrayList<BindingItem>>()

    val onErrorEvent = MutableLiveData<Event<String>>()
    val onBookmarkEvent = MutableLiveData<Event<Unit>>()

    val isLoading = MutableLiveData<Boolean>(false)

    fun getAllSearchUser(name: String) {
        if (name.isNotEmpty()) {
            isLoading.value = true

            addDisposable(getAllSearchUserUseCase.execute(name)
               .subscribe({
                   userItemList.value =
                           ArrayList(it.headerSort().toRecyclerItemList(this))
                   isLoading.value = false
                }, {
                   onErrorEvent.value = Event(it.message.toString())
                })
            )
        }
    }

    fun onClickSearch() {
        getAllSearchUser(userName.value!!)
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