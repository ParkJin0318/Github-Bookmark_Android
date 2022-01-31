package com.parkjin.github_bookmark.response

import com.google.gson.annotations.SerializedName

data class GithubResponse<T>(
    @SerializedName("total_count")
    val count: Int,
    @SerializedName("items")
    val items: T
)
