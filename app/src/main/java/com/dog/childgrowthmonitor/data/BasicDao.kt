package com.dog.childgrowthmonitor.data

import androidx.room.*

@Dao
interface BasicDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg obj: T)

    @Update (onConflict = OnConflictStrategy.IGNORE)
    fun update(obj: T): Int

    @Delete
    fun delete(obj: T)
}

@Transaction
inline fun <reified T> BasicDao<T>.insertOrUpdate(item: T){
    if(insert(item) != -1L) return
    update(item)
}