package com.parkjin.github_bookmark.mapper

import com.parkjin.github_bookmark.database.entity.UserEntity
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.network.response.UserResponse

fun User.toEntity(): UserEntity =
    UserEntity(
        this.name,
        this.profileImageUrl
    )

fun UserEntity.toModel(): User =
    User(
        this.name,
        this.profileImageUrl
    )

fun UserResponse.toModel(): User =
    User(
        this.name,
        this.profileImageUrl
    )
