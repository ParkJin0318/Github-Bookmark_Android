package com.parkjin.github_bookmark.presentation.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkjin.github_bookmark.domain.model.UserType
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetUsersUseCase
import com.parkjin.github_bookmark.presentation.core.Event
import com.parkjin.github_bookmark.presentation.extension.onNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val bookmarkUserUseCase: BookmarkUserUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val userList = mutableListOf<UserListItem>()

    private val _submit = MutableLiveData<List<UserListItem>>()
    val submit: LiveData<List<UserListItem>> get() = _submit

    private val _onErrorEvent = MutableLiveData<Event<Throwable>>()
    val onErrorEvent: LiveData<Event<Throwable>> get() = _onErrorEvent

    private var currentUserType = UserType.GITHUB

    val inputKeyword = MutableLiveData<String>()

    init {
        initEvent()
    }

    fun initUserType(type: UserType) {
        currentUserType = type
        if (type == UserType.BOOKMARK) loadUsers()
    }

    fun onClickSearch() {
        loadUsers(inputKeyword.value ?: "")
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun initEvent() {
        UserListItemManager.register()
            .filter { UserListItemManager.userType != currentUserType }
            .filter { UserListItemManager.userItem != null }
            .map { UserListItemManager.userItem!! }
            .onNetwork()
            .subscribe({ userItem ->
                val findItem = userList.toUserItems()
                    .find { it.user.name == userItem.user.name }

                when (UserListItemManager.userType) {
                    UserType.GITHUB -> {
                        findItem?.let { userList.removeUserItem(it) }
                            ?: let { userList.addUserItem(userItem) }
                    }
                    UserType.BOOKMARK -> {
                        findItem?.let {
                            val position = userList.indexOf(it)
                            userList.replaceUserItem(position, userItem)
                        }
                    }
                }

                _submit.value = userList
            }, Throwable::printStackTrace)
            .addTo(disposable)
    }

    private fun bookmarkToUser(item: UserListItem.UserItem) {
        bookmarkUserUseCase.execute(item.user, item.bookmarked)
            .onNetwork()
            .subscribe({
                val position = userList.indexOf(item)
                val newItem = item.copy(bookmarked = item.bookmarked.not())

                when (currentUserType) {
                    UserType.GITHUB -> {
                        userList.replaceUserItem(position, newItem)
                    }
                    UserType.BOOKMARK -> {
                        userList.removeUserItem(item)
                    }
                }

                _submit.value = userList
                UserListItemManager.onNext(currentUserType, newItem)
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposable)
    }

    private fun loadUsers(name: String = "") {
        if (userList.lastOrNull() == UserListItem.Loading) return

        val loadingItem = UserListItem.Loading
        userList.add(loadingItem)
        _submit.value = userList

        getUsersUseCase.execute(name, currentUserType)
            .onNetwork()
            .subscribe({
                userList.clear()
                userList.remove(loadingItem)
                userList.addAll(it.toUserListItems(::bookmarkToUser))
                _submit.value = userList
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposable)
    }
}
