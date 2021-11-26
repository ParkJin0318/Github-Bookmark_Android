package com.parkjin.github_bookmark.ui.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.extension.subscribe
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.UserType
import com.parkjin.github_bookmark.provider.SchedulerProvider
import com.parkjin.github_bookmark.ui.item.UserAdapter
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.ui.main.bookmarkUserSubject
import com.parkjin.github_bookmark.usecase.BookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val scheduler: SchedulerProvider,
    private val getUsersUseCase: GetUsersUseCase,
    private val bookmarkUserUseCase: BookmarkUserUseCase
) : ViewModel(), UserItemNavigator {

    private val currentUserType = UserType.GITHUB

    private val disposable = CompositeDisposable()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

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

    fun getUsersForName(name: String) {
        if (name.isEmpty()) return

        _isLoading.value = true

        getUsersUseCase.execute(name, currentUserType)
            .subscribe(
                scheduler = scheduler,
                disposable = disposable,
                onSuccess = {
                    adapter.notifyItemsChanged(it)
                    _isLoading.value = false
                },
                onError = {
                    _onErrorEvent.value = Event(it)
                }
            )
    }

    override fun onClickBookmark(name: String) {
        val user = adapter.currentList.find { it.name == name } ?: return

        bookmarkUserUseCase.execute(user)
            .subscribe(
                scheduler = scheduler,
                disposable = disposable,
                onSuccess = {
                    val newUser = user.copy(bookmarked = user.bookmarked.not())
                    adapter.replaceItem(user, newUser)

                    bookmarkUserSubject.onNext(Pair(newUser, currentUserType))
                },
                onError = {
                    _onErrorEvent.value = Event(it)
                }
            )
    }

    private fun updateBookmarkUser(data: Pair<User, UserType>) {
        data.let { (user, tab) ->
            if (tab == currentUserType) return

            adapter.currentList
                .find { it.name == user.name }
                ?.let {
                    val newUser = it.copy(bookmarked = it.bookmarked.not())
                    adapter.replaceItemChanged(it, newUser)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
