package com.parkjin.github_bookmark.presentation.ui.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkjin.github_bookmark.domain.model.User
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetGithubUsersUseCase
import com.parkjin.github_bookmark.presentation.ui.common.UserListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

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

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        setState(errorMessage = exception.message)
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
        if (userListModels.lastOrNull() == UserListModel.LoadingModel) return

        userListModels.add(UserListModel.LoadingModel)
        setState(userListModels = userListModels)

        getGithubUsersUseCase(name)
            .onEach { users ->
                userListModels.clear()

                users.map { user ->
                    UserListModel.UserModel(
                        user = user,
                        toggleBookmark = { setAction(Action.BookmarkUser(it.user)) }
                    )
                }
                    .sortedBy(UserListModel.UserModel::header)
                    .groupBy(UserListModel.UserModel::header)
                    .forEach { (header, users) ->
                        userListModels.add(UserListModel.HeaderModel(header))
                        userListModels.addAll(users)
                    }

                setState(userListModels = userListModels)
            }
            .launchIn(viewModelScope + exceptionHandler)
    }

    private fun bookmarkUser(user: User) {
        viewModelScope.launch(exceptionHandler) {
            bookmarkUserUseCase(user)
        }
    }
}
