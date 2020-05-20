package com.dog.childgrowthmonitor.data

import androidx.room.*

@Dao
interface BasicDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg obj: T): List<Long>

    @Update (onConflict = OnConflictStrategy.IGNORE)
    fun update(obj: T): Int

    @Delete
    fun delete(obj: T)
}