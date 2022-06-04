package com.parkjin.github_bookmark.remote.response

import com.google.gson.annotations.SerializedName
import com.parkjin.github_bookmark.data.model.GithubUser

data class GithubUserResponse(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val profileImageUrl: String
)

fun GithubUserResponse.toModel() =
    GithubUser(
        name,
        profileImageUrl
    )
