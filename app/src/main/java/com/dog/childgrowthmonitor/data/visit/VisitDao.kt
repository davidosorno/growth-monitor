package com.dog.childgrowthmonitor.data.visit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dog.childgrowthmonitor.data.BasicDao

@Dao
interface VisitDao: BasicDao<Visit> {

    @Transaction
    @Query("SELECT * FROM child WHERE idChild = :idChild")
    fun getChildVisits(idChild: Long): LiveData<ChildPreviousVisits>

//    @Transaction
//    @Query("SELECT idVisit FROM visit WHERE child_id = :idChild ORDER BY idVisit DESC LIMIT 1")
//    fun getLastVisit(idChild: Long): LiveData<Long?>

    @Transaction
    @Query("SELECT * FROM visit WHERE child_id = :idChild AND idVisit = :idVisit")
    fun getVisit(idChild: Long, idVisit: Long): Visit?
}