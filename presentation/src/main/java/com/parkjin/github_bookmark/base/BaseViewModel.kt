package com.parkjin.github_bookmark.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 보일러 플레이트 코드 제거를 위한 기본적인 ViewModel 클래스
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