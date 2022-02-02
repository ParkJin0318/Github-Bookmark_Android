package com.parkjin.github_bookmark.remote.response

import com.google.gson.annotations.SerializedName
import com.parkjin.github_bookmark.domain.model.User

data class GithubUser(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val profileImageUrl: String
)

fun GithubUser.toModel() =
    User(
        this.name,
        this.profileImageUrl
    )
