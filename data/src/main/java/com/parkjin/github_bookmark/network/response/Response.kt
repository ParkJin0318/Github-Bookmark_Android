package com.parkjin.github_bookmark.network.response

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("total_count")
    val count: Int,
    val items: T
)
