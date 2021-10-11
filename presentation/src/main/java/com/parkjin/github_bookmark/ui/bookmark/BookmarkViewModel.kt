package com.parkjin.github_bookmark.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkjin.github_bookmark.base.BaseViewModel
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.component.InputFieldViewModel
import com.parkjin.github_bookmark.extension.networkOn
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserAdapter
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.usecase.SelectBookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetBookmarkUsersUseCase

/**
 * BookmarkFragment의 ViewModel
 */
class BookmarkViewModel(
    private val getBookmarkUsersUseCase: GetBookmarkUsersUseCase,
    private val selectBookmarkUserUseCase: SelectBookmarkUserUseCase
) : BaseViewModel(), UserItemNavigator {

    private val _userName = MutableLiveData<String>("")
    val userName: LiveData<String>
        get() = _userName

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _onErrorEvent = MutableLiveData<Event<Throwable>>()
    val onErrorEvent: LiveData<Event<Throwable>>
        get() = _onErrorEvent

    private val _isUserEmpty = MutableLiveData<Boolean>(true)
    val isUserEmpty: LiveData<Boolean>
        get() = _isUserEmpty

    private val _adapter = UserAdapter(this)
    val adapter: UserAdapter
        get() = _adapter

    fun getBookmarkUsers(name: String) {
        _isLoading.value = true

        addDisposable(
            getBookmarkUsersUseCase.execute(name)
                .networkOn()
                .subscribe({
                    adapter.submitList(it)
                    _userName.value = name
                    _isUserEmpty.value = it.isEmpty()
                    _isLoading.value = false
                }, {
                    _onErrorEvent.value = Event(it)
                })
        )
    }

    override fun onClickBookmark(user: User) {
        addDisposable(selectBookmarkUserUseCase.execute(user)
            .networkOn()
            .subscribe({
                getBookmarkUsers(userName.value!!)
            }, {
                _onErrorEvent.value = Event(it)
            })
        )
    }
}