package com.parkjin.github_bookmark.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 기본적인 ViewModel 클래스
 */
abstract class BaseViewModel: ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}