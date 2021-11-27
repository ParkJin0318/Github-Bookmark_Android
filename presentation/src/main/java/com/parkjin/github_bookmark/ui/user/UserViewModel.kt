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

    private var currentUserType = UserType.GITHUB

    private val disposable = CompositeDisposable()

    private val _onErrorEvent = MutableLiveData<Event<Throwable>>()
    val onErrorEvent: LiveData<Event<Throwable>>
        get() = _onErrorEvent

    private val _adapter = UserAdapter(this)
    val adapter: UserAdapter
        get() = _adapter

    init {
        bookmarkUserSubject
            .distinctUntilChanged()
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.ui)
            .subscribe(this::updateBookmarkUser, Throwable::printStackTrace)
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

    private fun findUserHeader(firstName: String) = adapter.currentList
        .filterIsInstance<UserListUIModel.UserHeader>()
        .find { it.header == firstName }

    private fun findUserItem(find: (User) -> Boolean) = adapter.currentList
        .filterIsInstance<UserListUIModel.UserItem>()
        .find { find.invoke(it.user) }

    override fun onClickBookmark(name: String) {
        val model = findUserItem { it.name == name } ?: return
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

    private fun updateBookmarkUser(data: Pair<User, UserType>) {
        data.let { (user, type) ->
            val model = findUserItem { it.name == user.name }

            when (currentUserType) {
                UserType.GITHUB -> {
                    if (type == currentUserType) return

                    model?.let {
                        val newUser = it.user.copy(bookmarked = it.user.bookmarked.not())
                        adapter.replaceItemChanged(it, UserListUIModel.UserItem(newUser))
                    }
                }
                UserType.BOOKMARK -> {
                    model?.let {
                        adapter.removeItemChanged(model)

                        findUserItem { it.firstName == model.user.firstName }
                            ?: let {
                                findUserHeader(model.user.firstName)
                                    ?.let { adapter.removeItemChanged(it) }
                            }
                    } ?: let {
                        findUserHeader(user.firstName)
                            ?.let {
                                adapter.addItemChanged(it, UserListUIModel.UserItem(user))
                            } ?: let {
                                adapter.addItemsChanged(listOf(
                                    UserListUIModel.UserHeader(user.firstName),
                                    UserListUIModel.UserItem(user)
                                ))
                            }
                    }
                }
            }
        }
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
