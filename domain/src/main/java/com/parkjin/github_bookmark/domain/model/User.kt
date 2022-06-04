package com.parkjin.github_bookmark.domain.model

data class User(
    val name: String,
    val profileImageUrl: String,
    val bookmarked: Boolean
) {
    val header = name.first().uppercaseChar().toString()
}
