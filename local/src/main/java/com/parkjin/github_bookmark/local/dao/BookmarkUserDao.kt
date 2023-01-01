package com.parkjin.github_bookmark.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.parkjin.github_bookmark.local.entity.BookmarkUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkUser(entity: BookmarkUserEntity)

    @Query("SELECT * FROM bookmark_user_table")
    fun getBookmarkUsers(): Flow<List<BookmarkUserEntity>>

    @Query("SELECT * FROM bookmark_user_table where name like '%' || :keyword || '%'")
    fun getBookmarkUsers(keyword: String): Flow<List<BookmarkUserEntity>>

    @Delete
    suspend fun deleteBookmarkUser(entity: BookmarkUserEntity)
}
