package com.parkjin.github_bookmark.local.dao

import androidx.room.*
import com.parkjin.github_bookmark.local.entity.BookmarkUserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface BookmarkUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarkUser(entity: BookmarkUserEntity): Completable

    @Query("SELECT * FROM bookmark_user_table")
    fun getBookmarkUsers(): Single<List<BookmarkUserEntity>>

    @Query("SELECT * FROM bookmark_user_table where name like '%' || :keyword || '%'")
    fun getBookmarkUsers(keyword: String): Single<List<BookmarkUserEntity>>

    @Delete
    fun deleteBookmarkUser(entity: BookmarkUserEntity): Completable
}
