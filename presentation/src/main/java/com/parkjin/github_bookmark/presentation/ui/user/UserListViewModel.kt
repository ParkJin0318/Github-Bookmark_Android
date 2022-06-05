package com.parkjin.github_bookmark.presentation.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkjin.github_bookmark.domain.model.Result
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetBookmarkUsersUseCase
import com.parkjin.github_bookmark.domain.usecase.GetGithubUsersUseCase
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

    private val _userListItems = MutableStateFlow<List<UserListItem>>(emptyList())
    val userListItems = _userListItems.asStateFlow()

    private val _onErrorEvent = MutableSharedFlow<Throwable>()
    val onErrorEvent = _onErrorEvent.asSharedFlow()

    val name = MutableStateFlow("")

    private var currentTabType = MainTabType.GITHUB

    init {
        initEvent()
    }

    fun initUserType(type: MainTabType) {
        currentTabType = type
        if (type == MainTabType.BOOKMARK) loadUsers()
    }

    fun onClickSearch() {
        loadUsers(name.value)
    }

    private fun initEvent() {
        viewModelScope.launch {
            UserListItemManager.register()
                .filter { UserListItemManager.tabType != currentTabType }
                .filter { UserListItemManager.userItem != null }
                .map { UserListItemManager.userItem!! }
                .collect { userItem ->
                    val userListItems = _userListItems.value.toMutableList()
                    val findItem = userListItems.toUserItems()
                        .find { it.user.name == userItem.user.name }

                    when (UserListItemManager.tabType) {
                        MainTabType.GITHUB -> {
                            findItem?.let { userListItems.removeUserItem(it) }
                                ?: let { userListItems.addUserItem(userItem) }
                        }
                        MainTabType.BOOKMARK -> {
                            findItem?.let { userListItems.replaceUserItem(userItem) }
                        }
                    }
                    _userListItems.emit(userListItems.toList())
                }
        }
    }

    private fun bookmarkToUser(item: UserListItem.UserItem) {
        bookmarkUserUseCase(item.user)
            .onEach { result ->
                val userListItems = _userListItems.value.toMutableList()
                when (result) {
                    is Result.Loading -> {
                        // TODO
                    }
                    is Result.Success -> {
                        when (currentTabType) {
                            MainTabType.GITHUB -> {
                                userListItems.replaceUserItem(item)
                            }
                            MainTabType.BOOKMARK -> {
                                userListItems.removeUserItem(item)
                            }
                        }
                        _userListItems.emit(userListItems.toList())
                        UserListItemManager.emit(currentTabType, item)
                    }
                    is Result.Failure -> {
                        _onErrorEvent.emit(result.throwable)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun loadUsers(name: String = "") {
        val userListItems = _userListItems.value.toMutableList()
        if (userListItems.lastOrNull() == UserListItem.Loading) return

        val users =
            if (currentTabType == MainTabType.GITHUB) getGithubUsersUseCase(name)
            else getBookmarkUsersUseCase(name)

        users.onEach { result ->
            when (result) {
                is Result.Loading -> {
                    userListItems.add(UserListItem.Loading)
                    _userListItems.emit(userListItems.toList())
                }
                is Result.Success -> {
                    userListItems.clear()
                    userListItems.addAll(result.value.toUserListItems(::bookmarkToUser))
                    _userListItems.emit(userListItems.toList())
                }
                is Result.Failure -> {
                    _onErrorEvent.emit(result.throwable)
                }
            }
        }.launchIn(viewModelScope)
    }
}
