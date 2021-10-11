package com.parkjin.github_bookmark.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkjin.github_bookmark.base.BaseViewModel
import com.parkjin.github_bookmark.base.Event

class InputFieldViewModel : BaseViewModel() {

    val input = MutableLiveData<String>("")

    private val _onSearchEvent = MutableLiveData<Event<String?>>()
    val onSearchEvent: LiveData<Event<String?>>
        get() = _onSearchEvent

    fun onClickSearch() {
        _onSearchEvent.value = Event(input.value)
    }
}