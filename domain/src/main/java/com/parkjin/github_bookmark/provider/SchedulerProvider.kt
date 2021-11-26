package com.parkjin.github_bookmark.provider

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
    val ui: Scheduler
    val io: Scheduler
}
