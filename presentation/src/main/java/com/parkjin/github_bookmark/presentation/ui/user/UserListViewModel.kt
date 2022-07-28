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

sealed class UserListState {
    data class UserList(val list: List<UserListItem>) : UserListState()
    data class Bookmark(val tabType: MainTabType, val item: UserListItem.UserItem) : UserListState()
    data class Message(val message: String?) : UserListState()
}

@HiltViewModel
class UserListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGithubUsersUseCase: GetGithubUsersUseCase,
    private val getBookmarkUsersUseCase: GetBookmarkUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UserListState>(UserListState.UserList(emptyList()))
    val state = _state.asStateFlow()

    val name = MutableStateFlow("")

    private val userListItems = mutableListOf<UserListItem>()

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
        when (currentTabType) {
            MainTabType.GITHUB -> {
                userListItems.replaceUserItem(item)
            }
            MainTabType.BOOKMARK -> {
                userListItems.filterIsInstance<UserListItem.UserItem>()
                    .find { it.user.name == item.user.name }
                    ?.let { userListItems.removeUserItem(it) }
                    ?: let { userListItems.addUserItem(item) }
            }
        }

        viewModelScope.launch {
            _state.emit(UserListState.UserList(userListItems.toList()))
        }
    }

    fun bookmarkedUserForBookmarkTab(item: UserListItem.UserItem) {
        when (currentTabType) {
            MainTabType.GITHUB -> {
                userListItems.filterIsInstance<UserListItem.UserItem>()
                    .find { it.user.name == item.user.name }
                    ?.let { userListItems.replaceUserItem(item) }
            }
            MainTabType.BOOKMARK -> {
                userListItems.removeUserItem(item)
            }
        }

        viewModelScope.launch {
            _state.emit(UserListState.UserList(userListItems.toList()))
        }
    }

    private fun loadUsers(name: String = "") {
        if (userListItems.lastOrNull() == UserListItem.Loading) return

        val users =
            if (currentTabType == MainTabType.GITHUB) getGithubUsersUseCase(name)
            else getBookmarkUsersUseCase(name)

        users.onEach { result ->
            when (result) {
                is Result.Loading -> {
                    userListItems.add(UserListItem.Loading)
                    _state.emit(UserListState.UserList(userListItems.toList()))
                }
                is Result.Success -> {
                    userListItems.clear()
                    userListItems.addAll(result.value.toUserListItems {
                        viewModelScope.launch {
                            _state.emit(UserListState.Bookmark(currentTabType, it))
                        }
                    })
                    _state.emit(UserListState.UserList(userListItems.toList()))
                }
                is Result.Failure -> {
                    _state.emit(UserListState.Message(result.throwable.message))
                }
            }
        }.launchIn(viewModelScope)
    }
}
