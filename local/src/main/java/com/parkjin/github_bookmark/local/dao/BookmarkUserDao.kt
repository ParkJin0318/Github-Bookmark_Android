package com.parkjin.github_bookmark.local.dao

import androidx.room.*
import com.parkjin.github_bookmark.local.entity.BookmarkUserEntity

@Dao
interface BookmarkUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarkUser(entity: BookmarkUserEntity)

    @Query("SELECT * FROM bookmark_user_table")
    fun getBookmarkUsers(): List<BookmarkUserEntity>

    @Query("SELECT * FROM bookmark_user_table where name like '%' || :keyword || '%'")
    fun getBookmarkUsers(keyword: String): List<BookmarkUserEntity>

    @Delete
    fun deleteBookmarkUser(entity: BookmarkUserEntity)
}
