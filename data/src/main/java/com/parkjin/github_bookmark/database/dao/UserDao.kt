package com.parkjin.github_bookmark.database.dao

import androidx.room.*
import com.parkjin.github_bookmark.database.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(entity: UserEntity): Completable

    @Query("SELECT * FROM user_table")
    fun getUsers(): Single<List<UserEntity>>

    @Query("SELECT * FROM user_table where name like '%' || :name || '%'")
    fun getUsersForName(name: String): Single<List<UserEntity>>

    @Delete
    fun deleteUser(entity: UserEntity): Completable
}
