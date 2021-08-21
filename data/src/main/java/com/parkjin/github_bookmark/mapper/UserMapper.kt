package com.parkjin.github_bookmark.mapper

import com.parkjin.github_bookmark.database.entity.UserEntity
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.network.response.UserData

/**
 * User -> UserEntity 변환 Mapper
 */
fun User.toEntity(): UserEntity =
    UserEntity(
        this.name,
        this.profileImageUrl
    )

/**
 * UserEntity -> User 변환 Mapper
 */
fun UserEntity.toModel(): User =
    User(
        this.name,
        this.profileImageUrl
    )

/**
 * UserData -> User 변환 Mapper
 */
fun UserData.toModel(): User =
    User(
        this.name,
        this.profileImageUrl
    )