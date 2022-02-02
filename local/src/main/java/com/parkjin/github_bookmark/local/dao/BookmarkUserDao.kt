package com.parkjin.github_bookmark.local.dao

import androidx.room.*
import com.parkjin.github_bookmark.local.entity.BookmarkUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface BookmarkUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarkUser(entity: BookmarkUser): Completable

    @Query("SELECT * FROM bookmark_user_table")
    fun getBookmarkUsers(): Single<List<BookmarkUser>>

    @Query("SELECT * FROM bookmark_user_table where name like '%' || :name || '%'")
    fun getBookmarkUsers(name: String): Single<List<BookmarkUser>>

    @Delete
    fun deleteBookmarkUser(entity: BookmarkUser): Completable
}
