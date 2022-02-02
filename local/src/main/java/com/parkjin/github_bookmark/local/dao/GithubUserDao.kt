package com.parkjin.github_bookmark.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.parkjin.github_bookmark.local.entity.GithubUserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface GithubUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGithubUsers(entities: List<GithubUserEntity>): Completable

    @Query("SELECT * FROM github_user_table where keyword like :keyword")
    fun getGithubUsers(keyword: String): Single<List<GithubUserEntity>>

    @Query("DELETE FROM github_user_table where keyword like :keyword")
    fun deleteGithubUsers(keyword: String): Completable
}
