package com.parkjin.github_bookmark.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.extension.subscribe
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.UserType
import com.parkjin.github_bookmark.model.firstName
import com.parkjin.github_bookmark.provider.SchedulerProvider
import com.parkjin.github_bookmark.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val scheduler: SchedulerProvider,
    private val getUsersUseCase: GetUsersUseCase,
    private val bookmarkUserUseCase: BookmarkUserUseCase
) : ViewModel(), UserListAdapter.UserListener {

    val inputKeyword = MutableLiveData<String>()

    val adapter: UserListAdapter = UserListAdapter(this)

    private var currentUserType = UserType.GITHUB

    private val disposable = CompositeDisposable()

    private val _onErrorEvent = MutableLiveData<Event<Throwable>>()
    val onErrorEvent: LiveData<Event<Throwable>>
        get() = _onErrorEvent

    private val userListItems: MutableList<UserListItem> = mutableListOf()

    private val headerItems: List<UserListItem.UserHeader>
        get() = userListItems.filterIsInstance<UserListItem.UserHeader>()

    private val userItems: List<UserListItem.UserItem>
        get() = userListItems.filterIsInstance<UserListItem.UserItem>()

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

    override fun onClickBookmark(name: String) {
        val item = userItems.find { it.user.name == name } ?: return
        bookmarkToUser(item)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun loadUsers(name: String = "") {
        if (userListItems.lastOrNull() == UserListItem.Loading) return

        val loadingItem = UserListItem.Loading
        userListItems.add(loadingItem)
        submitList()

        getUsersUseCase.execute(name, currentUserType)
            .subscribe(
                scheduler = scheduler,
                disposable = disposable,
                onSuccess = {
                    userListItems.clear()
                    userListItems.remove(loadingItem)
                    userListItems.addAll(it.toUserListItems())
                    submitList()
                },
                onError = {
                    _onErrorEvent.value = Event(it)
                }
            )
    }

    private fun initEvent() {
        UserStore.register()
            .subscribe(
                scheduler = scheduler,
                disposable = disposable,
                onSuccess = {
                    if (UserStore.userType == currentUserType) return@subscribe

                    val userItem = userItems.find { it.user.name == UserStore.userItem.user.name }

                    when (UserStore.userType) {
                        UserType.GITHUB -> {
                            userItem?.let { userListItems.remove(it) }
                                ?: let {
                                    val headerItem = headerItems.find { it.header == UserStore.userItem.user.firstName }

                                    headerItem?.let {
                                        val position = userListItems.indexOf(it)
                                        userListItems.add(position.inc(), UserStore.userItem)
                                    } ?: let {
                                        val item = UserStore.userItem
                                        userListItems.add(UserListItem.UserHeader(item.user.firstName))
                                        userListItems.add(item)
                                    }
                                }
                        }
                        UserType.BOOKMARK -> {
                            userItem?.let {
                                val position = userListItems.indexOf(it)
                                userListItems[position] = UserStore.userItem
                            }
                        }
                    }
                    submitList()
                },
                onError = Throwable::printStackTrace
            )
    }

    private fun bookmarkToUser(item: UserListItem.UserItem) {
        bookmarkUserUseCase.execute(item.user)
            .subscribe(
                scheduler = scheduler,
                disposable = disposable,
                onSuccess = {
                    val position = userListItems.indexOf(item)
                    val newUserItem = UserListItem.UserItem(
                        user = item.user,
                        bookmarked = item.bookmarked.not()
                    )

                    when (currentUserType) {
                        UserType.GITHUB -> {
                            userListItems[position] = newUserItem
                            notifyUserBookmark(position)
                        }
                        UserType.BOOKMARK -> {
                            userListItems.removeAt(position)

                            userItems.find { it.user.firstName == item.user.firstName }
                                ?: let {
                                    headerItems.find { it.header == item.user.firstName }
                                        ?.let { userListItems.remove(it) }
                                }

                            submitList()
                        }
                    }

                    UserStore.update(currentUserType, newUserItem)
                },
                onError = {
                    _onErrorEvent.value = Event(it)
                }
            )
    }

    private fun submitList() {
        adapter.submitList(userListItems.toMutableList())
    }

    private fun notifyUserBookmark(position: Int) {
        adapter.notifyUserBookmark(position)
    }

    private fun List<User>.toUserListItems(): List<UserListItem> {
        val userListItems: MutableList<UserListItem> = mutableListOf()

        this.sortedBy(User::name)
            .groupBy(User::firstName)
            .forEach { (header, users) ->
                userListItems.add(UserListItem.UserHeader(header))
                userListItems.addAll(users.map(UserListItem::UserItem))
            }
        return userListItems
    }
}


