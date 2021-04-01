package com.parkjin.github_bookmark.repository

import com.parkjin.github_bookmark.model.User
import io.reactivex.Single

/**
 * 사용자 정보를 조회하기 위한 인터페이스,
 * Data 계층의 UserRepositoryImpl 클래스와 연결.
 */
interface UserRepository {
    fun getAllSearchUser(name: String): Single<List<User>>
    fun getAllBookmarkUser(name: String): Single<List<User>>
}