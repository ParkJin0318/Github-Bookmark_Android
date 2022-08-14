package com.parkjin.github_bookmark.presentation.ui.main

enum class MainTabType(val title: String) {
    GITHUB("Github"),
    BOOKMARK("Bookmark");

    companion object {
        fun from(ordinal: Int) = values().find { it.ordinal == ordinal } ?: GITHUB
    }
}
