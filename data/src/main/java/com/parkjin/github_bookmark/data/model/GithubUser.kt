package com.parkjin.github_bookmark.data.model

import com.parkjin.github_bookmark.domain.model.User
import java.util.Date

data class GithubUser(
    val name: String,
    val profileImageUrl: String,
    val savedAt: Date
)

fun GithubUser.toUser() =
    User(
        name,
        profileImageUrl
    )
