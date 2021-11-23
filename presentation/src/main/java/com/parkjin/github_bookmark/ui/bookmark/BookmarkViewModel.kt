package com.parkjin.github_bookmark.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.extension.networkOn
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserAdapter
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.usecase.SelectBookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetBookmarkUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkUsersUseCase: GetBookmarkUsersUseCase,
    private val selectBookmarkUserUseCase: SelectBookmarkUserUseCase
) : ViewModel(), UserItemNavigator {

    private val disposables = CompositeDisposable()

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

        getBookmarkUsersUseCase.execute(name)
            .networkOn()
            .subscribe({
                adapter.submitList(it)
                _userName.value = name
                _isUserEmpty.value = it.isEmpty()
                _isLoading.value = false
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposables)
    }

    override fun onClickBookmark(user: User) {
        selectBookmarkUserUseCase.execute(user)
            .networkOn()
            .subscribe({
                getBookmarkUsers(userName.value!!)
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
