package com.parkjin.github_bookmark.data.model

import com.parkjin.github_bookmark.domain.model.User

data class GithubUser(
    val name: String,
    val profileImageUrl: String
)

fun GithubUser.toUser() =
    User(
        name,
        profileImageUrl,
        false
    )
