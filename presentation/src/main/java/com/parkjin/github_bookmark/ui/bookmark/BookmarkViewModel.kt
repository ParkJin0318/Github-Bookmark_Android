package com.parkjin.github_bookmark.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkjin.github_bookmark.base.Event
import com.parkjin.github_bookmark.extension.networkOn
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.ui.item.UserAdapter
import com.parkjin.github_bookmark.ui.item.UserItemNavigator
import com.parkjin.github_bookmark.ui.main.TabType
import com.parkjin.github_bookmark.ui.main.bookmarkUserSubject
import com.parkjin.github_bookmark.usecase.SelectBookmarkUserUseCase
import com.parkjin.github_bookmark.usecase.GetBookmarkUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkUsersUseCase: GetBookmarkUsersUseCase,
    private val selectBookmarkUserUseCase: SelectBookmarkUserUseCase
) : ViewModel(), UserItemNavigator {

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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateUserToList, Throwable::printStackTrace)
            .addTo(disposable)
    }

    private fun updateUserToList(data: Pair<User, TabType>) {
        data.let { (user, _) ->
            adapter.currentList
                .find { it.name == user.name }
                ?.let { adapter.removeItemChanged(it) }
                ?: let { adapter.addItemChanged(user) }
        }
    }

    fun getBookmarkUsers(name: String = "") {
        _isLoading.value = true

        getBookmarkUsersUseCase.execute(name)
            .networkOn()
            .subscribe({
                adapter.replaceItemsChanged(it)
                _isLoading.value = false
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposable)
    }

    override fun onClickBookmark(user: User) {
        selectBookmarkUserUseCase.execute(user)
            .networkOn()
            .subscribe({
                val newUser = user.copy(bookmarked = user.bookmarked.not())
                adapter.replaceItem(user, newUser)

                bookmarkUserSubject.onNext(Pair(newUser, TabType.BOOKMARK))
            }, {
                _onErrorEvent.value = Event(it)
            }).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
