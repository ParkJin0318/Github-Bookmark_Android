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

private val bookmarkUserSubject: PublishSubject<Pair<User, UserType>> = PublishSubject.create()

@HiltViewModel
class UserViewModel @Inject constructor(
    private val scheduler: SchedulerProvider,
    private val getUsersUseCase: GetUsersUseCase,
    private val bookmarkUserUseCase: BookmarkUserUseCase
) : ViewModel(), UserAdapter.UserListener {

    val adapter: UserAdapter = UserAdapter(this)

    private var currentUserType = UserType.GITHUB

    private val disposable = CompositeDisposable()

    private val _onErrorEvent = MutableLiveData<Event<Throwable>>()
    val onErrorEvent: LiveData<Event<Throwable>>
        get() = _onErrorEvent

    init {
        bookmarkUserSubject
            .distinctUntilChanged()
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.ui)
            .subscribe(this::notifyUserBookmark, Throwable::printStackTrace)
            .addTo(disposable)
    }

    fun setUserType(type: UserType) {
        currentUserType = type
        if (type == UserType.BOOKMARK) getUsers()
    }

    fun getUsers(name: String = "") {
        setLoading(true)

        getUsersUseCase.execute(name, currentUserType)
            .subscribe(
                scheduler = scheduler,
                disposable = disposable,
                onSuccess = {
                    setLoading(false)
                    adapter.notifyItemsChanged(it.toUserListUIModels())
                },
                onError = {
                    _onErrorEvent.value = Event(it)
                }
            )
    }

    override fun onClickBookmark(name: String) {
        val model = adapter.currentList.findUserItem { it.name == name } ?: return
        val user = model.user

        bookmarkUserUseCase.execute(user)
            .subscribe(
                scheduler = scheduler,
                disposable = disposable,
                onSuccess = {
                    val newUser = user.copy(bookmarked = user.bookmarked.not())
                    adapter.replaceItem(model, UserListUIModel.UserItem(newUser))

                    bookmarkUserSubject.onNext(Pair(newUser, currentUserType))
                },
                onError = {
                    _onErrorEvent.value = Event(it)
                }
            )
    }

    private fun notifyUserBookmark(data: Pair<User, UserType>) {
        data.let { (user, bookmarkedType) ->
            val model = adapter.currentList.findUserItem { it.name == user.name }

            when (currentUserType) {
                UserType.GITHUB -> notifyGithubUser(model, bookmarkedType)
                UserType.BOOKMARK -> notifyBookmarkUser(user, model)
            }
        }
    }

    private fun notifyGithubUser(model: UserListUIModel.UserItem?, bookmarkedType: UserType) {
        if (model == null || bookmarkedType == UserType.GITHUB) return

        val user = model.user
        val newUser = user.copy(bookmarked = user.bookmarked.not())
        adapter.replaceItemChanged(model, UserListUIModel.UserItem(newUser))
    }

    private fun notifyBookmarkUser(user: User, model: UserListUIModel.UserItem?) {
        val header = adapter.currentList.findUserHeader(user.firstName)
        model?.let { removeBookmarkUser(it, header) } ?: addBookmarkUser(user, header)
    }

    private fun addBookmarkUser(user: User, header: UserListUIModel.UserHeader?) {
        if (header == null) {
            adapter.addItemsChanged(
                listOf(
                    UserListUIModel.UserHeader(user.firstName),
                    UserListUIModel.UserItem(user)
                )
            )
        } else {
            adapter.addItemChanged(header, UserListUIModel.UserItem(user))
        }
    }

    private fun removeBookmarkUser(model: UserListUIModel.UserItem, header: UserListUIModel.UserHeader?) {
        adapter.removeItemChanged(model)

        val notExistOtherUser = adapter.currentList
            .findUserItem { it.firstName == model.user.firstName } == null

        if (notExistOtherUser) header?.let { adapter.removeItemChanged(it) }
    }

    private fun setLoading(isLoading: Boolean) {
        val model = UserListUIModel.Loading

        if (isLoading) adapter.addItemChanged(model)
        else adapter.removeItemChanged(model)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

private fun List<User>.toUserListUIModels(): List<UserListUIModel> {
    val userListUIModels: MutableList<UserListUIModel> = mutableListOf()

    val users = LinkedList(this.sortedBy { it.name.uppercase() })

    while (!users.isEmpty()) {
        val user = users.remove()

        userListUIModels.add(UserListUIModel.UserHeader(user.firstName))
        userListUIModels.add(UserListUIModel.UserItem(user))

        while (users.peek() != null && user.firstName == users.peek()?.firstName) {
            userListUIModels.add(UserListUIModel.UserItem(users.remove()))
        }
    }

    return userListUIModels
}

private fun List<UserListUIModel>.findUserHeader(firstName: String) =
    this.filterIsInstance<UserListUIModel.UserHeader>()
        .find { it.header == firstName }

private fun List<UserListUIModel>.findUserItem(find: (User) -> Boolean) =
    this.filterIsInstance<UserListUIModel.UserItem>()
        .find { find.invoke(it.user) }
