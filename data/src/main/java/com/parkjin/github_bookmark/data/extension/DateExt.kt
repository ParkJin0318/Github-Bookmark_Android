package com.parkjin.github_bookmark.data.extension

import java.util.Date

fun Date?.diffHour(): Long {
    this ?: return 0

    val startTime = Date().time
    val endTime = this.time
    return (startTime - endTime) / 3600000
}

fun Date?.isPassedDay() = this.diffHour() >= 24
