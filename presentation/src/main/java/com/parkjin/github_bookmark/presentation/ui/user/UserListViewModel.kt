package com.parkjin.github_bookmark.presentation.ui.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkjin.github_bookmark.domain.model.Result
import com.parkjin.github_bookmark.domain.usecase.GetBookmarkUsersUseCase
import com.parkjin.github_bookmark.domain.usecase.GetGithubUsersUseCase
import com.parkjin.github_bookmark.presentation.ui.main.MainTabType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGithubUsersUseCase: GetGithubUsersUseCase,
    private val getBookmarkUsersUseCase: GetBookmarkUsersUseCase
) : ViewModel() {

    private val _userListItems = MutableStateFlow<List<UserListItem>>(emptyList())
    val userListItems = _userListItems.asStateFlow()

    private val _toggleBookmark = MutableSharedFlow<Pair<MainTabType, UserListItem.UserItem>>()
    val toggleBookmark = _toggleBookmark.asSharedFlow()

    private val _onErrorEvent = MutableSharedFlow<Throwable>()
    val onErrorEvent = _onErrorEvent.asSharedFlow()

    val name = MutableStateFlow("")

    private val currentTabType = savedStateHandle.get(MainTabType::class.java.name)
        ?: MainTabType.GITHUB

    init {
        currentTabType.takeIf { it == MainTabType.BOOKMARK }
            ?.let { loadUsers() }
    }

    fun onClickSearch() {
        loadUsers(name.value)
    }

    fun bookmarkedUserForGithubTab(item: UserListItem.UserItem) {
        val userListItems = _userListItems.value.toMutableList()

        when (currentTabType) {
            MainTabType.GITHUB -> {
                userListItems.replaceUserItem(item)
            }
            MainTabType.BOOKMARK -> {
                userListItems.toUserItems()
                    .find { it.user.name == item.user.name }
                    ?.let { userListItems.removeUserItem(it) }
                    ?: let { userListItems.addUserItem(item) }
            }
        }

        viewModelScope.launch {
            _userListItems.emit(userListItems.toList())
        }
    }

    fun bookmarkedUserForBookmarkTab(item: UserListItem.UserItem) {
        val userListItems = _userListItems.value.toMutableList()

        when (currentTabType) {
            MainTabType.GITHUB -> {
                userListItems.toUserItems()
                    .find { it.user.name == item.user.name }
                    ?.let { userListItems.replaceUserItem(item) }
            }
            MainTabType.BOOKMARK -> {
                userListItems.removeUserItem(item)
            }
        }

        viewModelScope.launch {
            _userListItems.emit(userListItems.toList())
        }
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
                    userListItems.addAll(result.value.toUserListItems {
                        viewModelScope.launch {
                            _toggleBookmark.emit(currentTabType to it)
                        }
                    })
                    _userListItems.emit(userListItems.toList())
                }
                is Result.Failure -> {
                    _onErrorEvent.emit(result.throwable)
                }
            }
        }.launchIn(viewModelScope)
    }
}
