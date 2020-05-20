package com.dog.childgrowthmonitor.data.user

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.dog.childgrowthmonitor.data.BasicDao

@Dao
interface UserDao: BasicDao<User> {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(user: User, visits: List<AnthroCalculator>): List<Long>

    @Transaction
    @Query("SELECT * FROM user ORDER BY firstName, lastName ASC")
    fun getAllUser(): LiveData<List<User>>

//    @Transaction
//    @Query("SELECT idUser FROM user ORDER BY idUser DESC LIMIT 1")
//    fun getLastUser(): LiveData<Long>

    @Transaction
    @Query("SELECT * FROM user WHERE idUser = :idUser")
    fun getUser(idUser: Long): User?


    @Transaction
    @RawQuery(observedEntities = [User::class])
    fun getAllParentByFilter(query: SupportSQLiteQuery): LiveData<List<User>>
}