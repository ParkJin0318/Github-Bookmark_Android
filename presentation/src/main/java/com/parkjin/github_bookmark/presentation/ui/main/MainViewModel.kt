package com.parkjin.github_bookmark.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkjin.github_bookmark.domain.model.Result
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.presentation.ui.user.UserListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bookmarkUserUseCase: BookmarkUserUseCase
) : ViewModel() {

    private val _bookmarkedUser = MutableSharedFlow<Pair<MainTabType, UserListItem.UserItem>>()
    val bookmarkedUser = _bookmarkedUser.asSharedFlow()

    fun toggleBookmark(tabType: MainTabType, userItem: UserListItem.UserItem) {
        bookmarkUserUseCase(userItem.user)
            .onEach {
                if (it is Result.Success) {
                    _bookmarkedUser.emit(tabType to userItem)
                }
            }.launchIn(viewModelScope)
    }
}