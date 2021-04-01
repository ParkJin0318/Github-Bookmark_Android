package com.parkjin.github_bookmark.network.response

import com.google.gson.annotations.SerializedName

/*
    보일러 플레이트 코드 제거를 위한 기본적인 Response 클래스
 */
data class Response<T>(
    @SerializedName("total_count")
    val count: Int,
    val items: T
)