package com.parkjin.github_bookmark.domain.model

data class User(
    val name: String,
    val profileImageUrl: String,
    val bookmarked: Boolean = false
)

val User.header: String
    get() = name.first().uppercaseChar().toString()

enum class UserType(val title: String) {
    GITHUB("Github"),
    BOOKMARK("Bookmark");
}
