package com.parkjin.github_bookmark.presentation.ui.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkjin.github_bookmark.domain.model.Result
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetGithubUsersUseCase
import com.parkjin.github_bookmark.presentation.ui.common.UserListModel
import com.parkjin.github_bookmark.presentation.ui.common.BookmarkObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class GithubUserListViewModel @Inject constructor(
    private val getGithubUsersUseCase: GetGithubUsersUseCase,
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
        BookmarkObserver.registerForGithubTab()
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

    private fun loadGithubUsers(name: String) {
        val userListModels = currentState.userListModels.toMutableList()
        if (userListModels.lastOrNull() == UserListModel.Loading) return

        getGithubUsersUseCase(name).onEach { result ->
            when (result) {
                is Result.Loading -> {
                    userListModels.add(UserListModel.Loading)
                    setState(userListModels = userListModels)
                }
                is Result.Success -> {
                    userListModels.clear()
                    result.value.sortedBy(User::name)
                        .groupBy(User::header)
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
                BookmarkObserver.emitFoGithubTab(user)
            }
        }.launchIn(viewModelScope)
    }

    private fun handleBookmarkUser(user: User) {
        val userListModels = currentState.userListModels.toMutableList()

        val index = userListModels.indexOfFirst {
            if (it is UserListModel.Item) {
                it.user.name == user.name
            } else {
                false
            }
        }

        val model = userListModels.getOrNull(index) as? UserListModel.Item
            ?: return

        userListModels[index] = model.copy(user = user)
        setState(userListModels = userListModels)
    }
}