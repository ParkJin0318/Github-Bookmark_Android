package com.parkjin.github_bookmark.model

data class User(
    val name: String,
    val profileImageUrl: String,
    val bookmarked: Boolean = false
)

val User.firstName: String
    get() = name.first().uppercaseChar().toString()

enum class UserType(val title: String) {
    GITHUB("Github"),
    BOOKMARK("Bookmark");
}
