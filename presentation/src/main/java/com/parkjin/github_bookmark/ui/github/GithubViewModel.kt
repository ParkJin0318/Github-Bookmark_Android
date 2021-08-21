package com.parkjin.github_bookmark.ui.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkjin.github_bookmark.base.BaseViewModel
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.extension.networkOn
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserAdapter
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.usecase.SelectBookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetUsersUseCase

/**
 * GithubFragment의 ViewModel
 */
class GithubViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val selectBookmarkUserUseCase: SelectBookmarkUserUseCase
) : BaseViewModel(), UserItemNavigator {

    val inputName = MutableLiveData<String>("")

    private val _userName = MutableLiveData<String>("")
    val userName: LiveData<String>
        get() = _userName

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _onErrorEvent = MutableLiveData<Event<Throwable>>()
    val onErrorEvent: LiveData<Event<Throwable>>
        get() = _onErrorEvent

    private val _adapter = UserAdapter(this)
    val adapter: UserAdapter
        get() = _adapter

    fun getUsersForName(name: String) {
        if (name.isEmpty()) return

        _isLoading.value = true

        addDisposable(
            getUsersUseCase.execute(name)
                .networkOn()
                .subscribe({
                    adapter.submitList(it)
                    _userName.value = name
                    _isLoading.value = false
                }, {
                    _onErrorEvent.value = Event(it)
                })
        )
    }

    fun onClickSearch() {
        getUsersForName(inputName.value!!)
    }

    override fun onClickBookmark(user: User) {
        addDisposable(selectBookmarkUserUseCase.execute(user)
            .networkOn()
            .subscribe({
                getUsersForName(userName.value!!)
            }, {
                _onErrorEvent.value = Event(it)
            })
        )
    }
}