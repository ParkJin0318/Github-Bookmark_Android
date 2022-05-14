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

    private val _userListItems = MutableLiveData<List<UserListItem>>()
    val userListItems: LiveData<List<UserListItem>> get() = _userListItems

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
        val userListItems = this.userListItems.value?.toMutableList() ?: return

        UserListItemManager.register()
            .filter { UserListItemManager.userType != currentUserType }
            .filter { UserListItemManager.userItem != null }
            .map { UserListItemManager.userItem!! }
            .onNetwork()
            .subscribe({ userItem ->
                val findItem = userListItems.toUserItems()
                    .find { it.user.name == userItem.user.name }

                when (UserListItemManager.userType) {
                    UserType.GITHUB -> {
                        findItem?.let { userListItems.removeUserItem(it) }
                            ?: let { userListItems.addUserItem(userItem) }
                    }
                    UserType.BOOKMARK -> {
                        findItem?.let {
                            val position = userListItems.indexOf(it)
                            userListItems.replaceUserItem(position, userItem)
                        }
                    }
                }

                _userListItems.value = userListItems
            }, Throwable::printStackTrace)
            .addTo(disposable)
    }

    private fun bookmarkToUser(item: UserListItem.UserItem) {
        val userListItems = this.userListItems.value?.toMutableList() ?: return

        bookmarkUserUseCase.execute(item.user, item.bookmarked)
            .onNetwork()
            .subscribe({
                val position = userListItems.indexOf(item)
                val newItem = item.copy(bookmarked = item.bookmarked.not())

                when (currentUserType) {
                    UserType.GITHUB -> {
                        userListItems.replaceUserItem(position, newItem)
                    }
                    UserType.BOOKMARK -> {
                        userListItems.removeUserItem(item)
                    }
                }

                _userListItems.value = userListItems
                UserListItemManager.onNext(currentUserType, newItem)
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposable)
    }

    private fun loadUsers(name: String = "") {
        val userListItems = this.userListItems.value?.toMutableList() ?: return

        if (userListItems.lastOrNull() == UserListItem.Loading) return

        val loadingItem = UserListItem.Loading
        userListItems.add(loadingItem)
        _userListItems.value = userListItems

        getUsersUseCase.execute(name, currentUserType)
            .onNetwork()
            .subscribe({
                userListItems.clear()
                userListItems.remove(loadingItem)
                userListItems.addAll(it.toUserListItems(::bookmarkToUser))
                _userListItems.value = userListItems
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposable)
    }
}
