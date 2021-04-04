package com.parkjin.github_bookmark.ui.github

import androidx.lifecycle.MutableLiveData
import com.parkjin.github_bookmark.base.BaseViewModel
import com.parkjin.github_bookmark.base.BindingItem
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.extension.headerSort
import com.parkjin.github_bookmark.extension.toRecyclerItemList
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.usecase.AddBookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetAllSearchUserUseCase
import kotlin.collections.ArrayList

/**
 * GithubFragment의 ViewModel
 */
class GithubViewModel(
    private val getAllSearchUserUseCase: GetAllSearchUserUseCase,
    private val addBookmarkUserUseCase: AddBookmarkUserUseCase
): BaseViewModel(), UserItemNavigator {
    val inputName = MutableLiveData<String>("")
    val userName = MutableLiveData<String>("")

    val userItemList = MutableLiveData<ArrayList<BindingItem>>()

    val isLoading = MutableLiveData<Boolean>(false)
    val onErrorEvent = MutableLiveData<Event<String>>()

    fun getAllSearchUser(name: String) {
        if (name.isEmpty()) return

        isLoading.value = true

        addDisposable(getAllSearchUserUseCase.execute(name)
            .subscribe({
                userItemList.value = ArrayList(it.headerSort().toRecyclerItemList(this))
                userName.value = name
                isLoading.value = false
            }, {
                onErrorEvent.value = Event(it.message.toString())
            })
        )
    }

    fun onClickSearch() {
        getAllSearchUser(inputName.value!!)
    }

    override fun onClickBookmark(user: User) {
        addDisposable(addBookmarkUserUseCase.execute(user)
            .subscribe({
                getAllSearchUser(userName.value!!)
            }, {
                onErrorEvent.value = Event(it.message.toString())
            }))
    }
}