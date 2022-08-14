package com.parkjin.github_bookmark.presentation.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkjin.github_bookmark.domain.model.Result
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetBookmarkUsersUseCase
import com.parkjin.github_bookmark.presentation.ui.common.BookmarkObserver
import com.parkjin.github_bookmark.presentation.ui.common.UserListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class BookmarkUserListViewModel @Inject constructor(
    private val getBookmarkUsersUseCase: GetBookmarkUsersUseCase,
    private val bookmarkUserUseCase: BookmarkUserUseCase
) : ViewModel() {

    sealed class Action {
        data class SearchUserList(val name: String) : Action()
        data class BookmarkUser(val user: User) : Action()
    }

    data class State(
        val userListModels: List<UserListModel>,
        val errorMessage: String?
    )

    private val _state = MutableStateFlow(
        State(
            userListModels = emptyList(),
            errorMessage = null
        )
    )
    val state: StateFlow<State> = _state.asStateFlow()

    private val currentState: State get() = state.value

    init {
        loadGithubUsers()

        BookmarkObserver.registerForBookmarkTab()
            .onEach(::handleBookmarkUser)
            .launchIn(viewModelScope)
    }

    fun setAction(action: Action) {
        when (action) {
            is Action.SearchUserList -> {
                loadGithubUsers(action.name)
            }
            is Action.BookmarkUser -> {
                bookmarkUser(action.user)
            }
        }
    }

    private fun setState(
        userListModels: List<UserListModel> = currentState.userListModels,
        errorMessage: String? = currentState.errorMessage
    ) {
        viewModelScope.launch {
            _state.emit(
                currentState.copy(
                    userListModels = userListModels.toList(),
                    errorMessage = errorMessage
                )
            )
        }
    }

    private fun loadGithubUsers(name: String = "") {
        val userListModels = currentState.userListModels.toMutableList()
        if (userListModels.lastOrNull() == UserListModel.Loading) return

        getBookmarkUsersUseCase(name).onEach { result ->
            when (result) {
                is Result.Loading -> {
                    userListModels.add(UserListModel.Loading)
                    setState(userListModels = userListModels)
                }
                is Result.Success -> {
                    userListModels.clear()
                    result.value
                        .sortedBy(User::name)
                        .groupBy(User::header)
                        .entries
                        .forEach { (header, users) ->
                            UserListModel.Header(header)
                                .let(userListModels::add)

                            users.map { user ->
                                UserListModel.Item(user) {
                                    setAction(Action.BookmarkUser(it.user))
                                }
                            }.let(userListModels::addAll)
                        }

                    setState(userListModels = userListModels)
                }
                is Result.Failure -> {
                    setState(errorMessage = result.throwable.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun bookmarkUser(user: User) {
        bookmarkUserUseCase(user).onEach { result ->
            if (result is Result.Success) {
                handleBookmarkUser(user)
                BookmarkObserver.emitForBookmarkTab(user)
            }
        }.launchIn(viewModelScope)
    }

    private fun handleBookmarkUser(user: User) {
        val userListModels = currentState.userListModels.toMutableList()

        val userModel = userListModels.find {
            if (it is UserListModel.Item) {
                it.user.name == user.name
            } else {
                false
            }
        } as? UserListModel.Item

        val headerModel = userListModels.find {
            if (it is UserListModel.Header) {
                it.header == user.header
            } else {
                false
            }
        } as? UserListModel.Header

        if (userModel == null) {
            if (headerModel == null) {
                UserListModel.Header(user.header)
                    .let(userListModels::add)
            }

            UserListModel.Item(user) { model ->
                setAction(Action.BookmarkUser(model.user))
            }.let(userListModels::add)

            userListModels.sortBy { it.orderName }
        } else {
            userListModels.remove(userModel)

            val sameHeaderUser = userListModels.find {
                if (it is UserListModel.Item) {
                    it.user.header == userModel.user.header
                } else {
                    false
                }
            } as? UserListModel.Item

            if (sameHeaderUser == null && headerModel != null) {
                userListModels.remove(headerModel)
            }
        }
        setState(userListModels = userListModels)
    }
}