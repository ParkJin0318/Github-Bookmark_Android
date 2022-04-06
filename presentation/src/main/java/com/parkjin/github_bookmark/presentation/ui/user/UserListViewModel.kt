package com.parkjin.github_bookmark.presentation.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkjin.github_bookmark.domain.model.UserType
import com.parkjin.github_bookmark.domain.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.domain.usecase.GetUsersUseCase
import com.parkjin.github_bookmark.presentation.extension.debounce
import com.parkjin.github_bookmark.presentation.extension.onNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val bookmarkUserUseCase: BookmarkUserUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val bookmarkToUser: PublishSubject<UserListItem.UserItem> = PublishSubject.create()
    private val userListItems: MutableList<UserListItem> = mutableListOf()

    private val _onErrorEvent = MutableLiveData<com.parkjin.github_bookmark.presentation.core.Event<Throwable>>()
    val onErrorEvent: LiveData<com.parkjin.github_bookmark.presentation.core.Event<Throwable>>
        get() = _onErrorEvent

    private var currentUserType = UserType.GITHUB

    val inputKeyword = MutableLiveData<String>()
    val adapter = UserListAdapter(object : UserListAdapter.UserListener {
        override fun onClickBookmark(name: String) {
            val item = userListItems.toUserItems()
                .find { it.user.name == name }
                ?: return

            bookmarkToUser.onNext(item)
        }
    })

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

                submitList()
            }, Throwable::printStackTrace)
            .addTo(disposable)

        bookmarkToUser.distinctUntilChanged()
            .debounce(100L)
            .subscribe(this::bookmarkToUser, Throwable::printStackTrace)
            .addTo(disposable)
    }

    private fun bookmarkToUser(item: UserListItem.UserItem) {
        bookmarkUserUseCase.execute(item.user, item.bookmarked)
            .onNetwork()
            .subscribe({
                val position = userListItems.indexOf(item)
                val newUserItem = UserListItem.UserItem(
                    user = item.user,
                    bookmarked = item.bookmarked.not()
                )

                when (currentUserType) {
                    UserType.GITHUB -> {
                        userListItems.replaceUserItem(position, newUserItem)
                        notifyUserBookmark(position)
                    }
                    UserType.BOOKMARK -> {
                        userListItems.removeUserItem(item)
                        submitList()
                    }
                }

                UserListItemManager.onNext(currentUserType, newUserItem)
            }, {
                _onErrorEvent.value = com.parkjin.github_bookmark.presentation.core.Event(it)
            }).addTo(disposable)
    }

    private fun loadUsers(name: String = "") {
        if (userListItems.lastOrNull() == UserListItem.Loading) return

        val loadingItem = UserListItem.Loading
        userListItems.add(loadingItem)
        submitList()

        getUsersUseCase.execute(name, currentUserType)
            .onNetwork()
            .subscribe({
                userListItems.clear()
                userListItems.remove(loadingItem)
                userListItems.addAll(it.toUserListItems())
                submitList()
            }, {
                _onErrorEvent.value = com.parkjin.github_bookmark.presentation.core.Event(it)
            }).addTo(disposable)
    }

    private fun submitList() {
        adapter.submitList(userListItems.toMutableList())
    }

    private fun notifyUserBookmark(position: Int) {
        adapter.notifyUserBookmark(position)
    }
}
