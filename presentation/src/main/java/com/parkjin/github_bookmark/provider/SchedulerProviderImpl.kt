package com.parkjin.github_bookmark.provider

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProviderImpl @Inject constructor() : SchedulerProvider {

    override val ui: Scheduler = AndroidSchedulers.mainThread()
    override val io: Scheduler = Schedulers.io()
}
