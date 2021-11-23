package com.parkjin.github_bookmark.ui.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.extension.networkOn
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserAdapter
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.usecase.SelectBookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val selectBookmarkUserUseCase: SelectBookmarkUserUseCase
) : ViewModel(), UserItemNavigator {

    private val disposables = CompositeDisposable()

    val inputName = MutableLiveData("")

    private val _userName = MutableLiveData("")
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

        getUsersUseCase.execute(name)
            .networkOn()
            .subscribe({
                adapter.submitList(it)
                _userName.value = name
                _isLoading.value = false
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposables)
    }

    fun onClickSearch() {
        getUsersForName(inputName.value!!)
    }

    override fun onClickBookmark(user: User) {
        selectBookmarkUserUseCase.execute(user)
            .networkOn()
            .subscribe({
                getUsersForName(userName.value!!)
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
