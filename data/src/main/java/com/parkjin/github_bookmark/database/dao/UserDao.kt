package com.parkjin.github_bookmark.database.dao

import androidx.room.*
import com.parkjin.github_bookmark.database.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * DB를 엑세스하기 위한 함수를 포함하는 인터페이스,
 * 즐겨찾기 된 사용자를 저장, 조회, 삭제
 */
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