package com.dog.childgrowthmonitor.data.visit

import android.content.Context
import androidx.lifecycle.LiveData
import com.dog.childgrowthmonitor.data.GrowthMonitorDataBase

class VisitRepository(
    val visitDao: VisitDao
) {

    companion object {
        @Volatile
        private var instance: VisitRepository? = null

        fun getInstance(context: Context): VisitRepository? {
            return instance ?: synchronized(VisitRepository::class.java){
                if(instance == null){
                    val database = GrowthMonitorDataBase.getInstance(context)
                    instance = VisitRepository(database.visitDao())
                }
                return instance
            }
        }
    }

    fun getChildVisits(idChild:Long): LiveData<ChildPreviousVisits> {
        return visitDao.getChildVisits(idChild)
    }

    fun insert(visit: Visit): Long{
        return visitDao.insert(visit)
    }

    fun update(visit: Visit): Int {
        return visitDao.update(visit)
    }
}