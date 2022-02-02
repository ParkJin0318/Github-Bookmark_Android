package com.parkjin.github_bookmark.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.parkjin.github_bookmark.data.model.GithubUser
import java.util.Date

@Entity(tableName = "github_user_table")
data class GithubUserEntity(
    @PrimaryKey
    val name: String,
    val keyword: String,
    val profileImageUrl: String,
    val savedAt: Long
)

fun GithubUserEntity.toModel() =
    GithubUser(
        name,
        profileImageUrl,
        Date(savedAt)
    )

fun GithubUser.toEntity(keyword: String) =
    GithubUserEntity(
        name,
        keyword,
        profileImageUrl,
        savedAt.time
    )
