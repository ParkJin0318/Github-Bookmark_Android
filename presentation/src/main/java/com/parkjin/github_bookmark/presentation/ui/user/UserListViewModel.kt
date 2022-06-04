package com.parkjin.github_bookmark.presentation.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkjin.github_bookmark.domain.model.Result
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetBookmarkUsersUseCase
import com.parkjin.github_bookmark.domain.usecase.GetGithubUsersUseCase
import com.parkjin.github_bookmark.presentation.core.Event
import com.parkjin.github_bookmark.presentation.ui.main.MainTabType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getGithubUsersUseCase: GetGithubUsersUseCase,
    private val getBookmarkUsersUseCase: GetBookmarkUsersUseCase,
    private val bookmarkUserUseCase: BookmarkUserUseCase
) : ViewModel() {

    private val userList = mutableListOf<UserListItem>()

    private val _submit = MutableLiveData<List<UserListItem>>()
    val submit: LiveData<List<UserListItem>> get() = _submit

    private val _onErrorEvent = MutableLiveData<Event<Throwable>>()
    val onErrorEvent: LiveData<Event<Throwable>> get() = _onErrorEvent

    private var currentTabType = MainTabType.GITHUB

    val inputKeyword = MutableLiveData<String>()

    init {
        initEvent()
    }

    fun initUserType(type: MainTabType) {
        currentTabType = type
        if (type == MainTabType.BOOKMARK) loadUsers()
    }

    fun onClickSearch() {
        loadUsers(inputKeyword.value ?: "")
    }

    private fun initEvent() {
        viewModelScope.launch {
            UserListItemManager.register()
                .filter { UserListItemManager.tabType != currentTabType }
                .filter { UserListItemManager.userItem != null }
                .map { UserListItemManager.userItem!! }
                .collect { userItem ->
                    val findItem = userList.toUserItems()
                        .find { it.user.name == userItem.user.name }

                    when (UserListItemManager.tabType) {
                        MainTabType.GITHUB -> {
                            findItem?.let { userList.removeUserItem(it) }
                                ?: let { userList.addUserItem(userItem) }
                        }
                        MainTabType.BOOKMARK -> {
                            findItem?.let { userList.replaceUserItem(userItem) }
                        }
                    }
                    _submit.value = userList
                }
        }
    }

    private fun bookmarkToUser(item: UserListItem.UserItem) {
        bookmarkUserUseCase(item.user)
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {
                        // TODO
                    }
                    is Result.Success -> {
                        when (currentTabType) {
                            MainTabType.GITHUB -> {
                                userList.replaceUserItem(item)
                            }
                            MainTabType.BOOKMARK -> {
                                userList.removeUserItem(item)
                            }
                        }
                        _submit.value = userList
                        UserListItemManager.emit(currentTabType, item)
                    }
                    is Result.Failure -> {
                        _onErrorEvent.value = Event(result.throwable)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun loadUsers(name: String = "") {
        if (userList.lastOrNull() == UserListItem.Loading) return

        val users =
            if (currentTabType == MainTabType.GITHUB) getGithubUsersUseCase(name)
            else getBookmarkUsersUseCase(name)

        users.onEach { result ->
            when (result) {
                is Result.Loading -> {
                    userList.add(UserListItem.Loading)
                    _submit.value = userList
                }
                is Result.Success -> {
                    userList.clear()
                    userList.addAll(result.value.toUserListItems(::bookmarkToUser))
                    _submit.value = userList
                }
                is Result.Failure -> {
                    _onErrorEvent.value = Event(result.throwable)
                }
            }
        }.launchIn(viewModelScope)
    }
}
