package com.parkjin.github_bookmark.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubResponse<T>(
    @SerialName("total_count")
    val count: Int,
    @SerialName("items")
    val items: List<T>
)
