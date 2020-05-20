package com.dog.childgrowthmonitor.data.child

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dog.childgrowthmonitor.data.BasicDao

@Dao
interface ChildDao: BasicDao<Child> {

    @Transaction
    @Query("SELECT * FROM child ORDER BY firstName, lastName ASC")
    fun getChildren(): LiveData<List<Child>>


    @Transaction
    @Query("SELECT * FROM child WHERE idChild = :idChild")
    fun getChild(idChild: Long): LiveData<Child>


//    @Transaction
//    @RawQuery(observedEntities = [Child::class])
//    fun getParentsByFilter(query: SupportSQLiteQuery): LiveData<List<Child>>

//    @Transaction
//    @Query("SELECT * FROM child where idChild = :id")
//    fun getParents(id: Long): LiveData<ChildParents>

    @Transaction
    @Query("SELECT * FROM child where idChild = :id")
    fun getParents(id: Long): LiveData<ChildParents>
}