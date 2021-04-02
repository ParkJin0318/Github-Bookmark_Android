package com.parkjin.github_bookmark.model

/**
 * 사용자 인터페이스에 표시되는 모델,
 * UI에 필요한 User 정보를 가짐.
 */
data class User(
    val name: String,
    val profileImageUrl: String,
    val isBookmark: Boolean,
    var isHeaderShow: Boolean = false
) {
    fun nameFirst() : Char =
            name.first().toUpperCase()
}