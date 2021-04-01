package com.parkjin.github_bookmark.network.response

import com.google.gson.annotations.SerializedName

/*
    User 정보를 받아오는 Response 클래스
 */
data class UserData(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val profileImageUrl: String
)