package com.parkjin.github_bookmark.domain.repository

import com.parkjin.github_bookmark.domain.model.User

interface BookmarkUserRepository {

    fun getUsers(name: String): List<User>

    fun bookmarkUser(user: User)

    fun unBookmarkUser(user: User)
}
