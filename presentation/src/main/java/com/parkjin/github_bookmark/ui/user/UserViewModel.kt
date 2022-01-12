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
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
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

    private val userItems: List<UserListItem.UserItem>
        get() = userListItems.filterIsInstance<UserListItem.UserItem>()

    init {
        initEvent()
    }

    private fun initEvent() {
        BookmarkStore.register()
            .subscribeOn(scheduler.io)
            .subscribe({
                handleBookmarkEvent(BookmarkStore.userItem, BookmarkStore.userType)
            }, Throwable::printStackTrace)
            .addTo(disposable)
    }

    fun setUserType(type: UserType) {
        currentUserType = type
        if (type == UserType.BOOKMARK) loadUsers()
    }

    fun onClickSearch() {
        loadUsers(inputKeyword.value ?: "")
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

    override fun onClickBookmark(name: String) {
        val item = userItems.find { it.user.name == name } ?: return
        bookmarkToUser(item, userListItems.indexOf(item))
    }

    private fun bookmarkToUser(userItem: UserListItem.UserItem, index: Int) {
        bookmarkUserUseCase.execute(userItem.user)
            .subscribe(
                scheduler = scheduler,
                disposable = disposable,
                onSuccess = {
                    val item = UserListItem.UserItem(userItem.user, userItem.bookmarked.not())

                    when (currentUserType) {
                        UserType.GITHUB -> {
                            userListItems[index] = item
                            adapter.notifyBookmark(item)
                        }
                        UserType.BOOKMARK -> {
                            userListItems.removeAt(index)
                            submitList()
                        }
                    }
                    BookmarkStore.update(currentUserType, item)
                },
                onError = {
                    _onErrorEvent.value = Event(it)
                }
            )
    }

    private fun handleBookmarkEvent(userItem: UserListItem.UserItem?, type: UserType) {
        if (userItem == null || type == currentUserType) return

        val item = userItems.find { it.user.name == userItem.user.name }

        when (type) {
            UserType.GITHUB -> {
                item?.let { userListItems.remove(it) }
                    ?: userListItems.add(userItem)
            }
            UserType.BOOKMARK -> {
                item?.let {
                    val index = userListItems.indexOf(it)
                    userListItems[index] = userItem
                    adapter.notifyBookmark(userItem)
                }
            }
        }
        submitList()
    }

    private fun submitList() {
        adapter.submitList(userListItems.toMutableList())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun List<User>.toUserListItems(): List<UserListItem> {
        val userListItems: MutableList<UserListItem> = mutableListOf()

        this.sortedBy(User::name)
            .groupBy(User::firstName)
            .entries
            .forEach { (header, users) ->
                userListItems.add(UserListItem.UserHeader(header))
                userListItems.addAll(users.map(UserListItem::UserItem))
            }
        return userListItems
    }
}


