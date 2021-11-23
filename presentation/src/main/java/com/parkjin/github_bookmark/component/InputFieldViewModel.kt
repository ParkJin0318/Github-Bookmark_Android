package com.parkjin.github_bookmark.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkjin.github_bookmark.base.Event

class InputFieldViewModel : ViewModel() {

    val input = MutableLiveData<String>("")

    private val _onSearchEvent = MutableLiveData<Event<String?>>()
    val onSearchEvent: LiveData<Event<String?>>
        get() = _onSearchEvent

    fun onClickSearch() {
        _onSearchEvent.value = Event(input.value)
    }
}
