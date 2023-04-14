package com.parkjin.github_bookmark.remote.response

import com.parkjin.github_bookmark.data.model.GithubUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserResponse(
    @SerialName("login")
    val name: String,
    @SerialName("avatar_url")
    val profileImageUrl: String
)

fun GithubUserResponse.toModel() =
    GithubUser(
        name,
        profileImageUrl
    )
