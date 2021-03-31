package com.parkjin.github_bookmark.model

/*
    사용자 인터페이스에 표시되는 모델 클래스,
    UI에 필요한 User 정보를 가지고 있음.
 */
data class User(
    val name: String,
    val profileImageUrl: String,
    val isBookmark: Boolean
)