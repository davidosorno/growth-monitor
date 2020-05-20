package com.dog.childgrowthmonitor.data.parents

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.dog.childgrowthmonitor.data.BasicDao

@Dao
interface ParentDao: BasicDao<Parent>{

    @Transaction
    @Query("SELECT * FROM parent WHERE idParent = :idParent")
    fun getParent(idParent: Long): Parent?

    @Transaction
    @Query("SELECT * FROM parent")
    fun getChildren(): List<ParentChildren>

    @RawQuery(observedEntities = [Parent::class])
    fun getAllParentsFilterBy(query: SimpleSQLiteQuery): LiveData<List<Parent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(parentChild: ParentChildrenCrossRef): Long

//    @Transaction
//    @Query("DELETE FROM ParentChildrenCrossRef WHERE idChild = :idChild")
//    fun removeAllParents(idChild: Long)

    @Transaction
    @Query("DELETE FROM ParentChildrenCrossRef WHERE idChild = :idChild AND idParent = :idParent")
    fun removeParent(idChild: Long, idParent: Long)

    @Transaction
    @Query("DELETE FROM ParentChildrenCrossRef")
    fun removeParents()

}