package com.dog.childgrowthmonitor.data.parents

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.dog.childgrowthmonitor.data.GrowthMonitorDataBase
import com.dog.childgrowthmonitor.ui.search.SearchFilter
import com.dog.childgrowthmonitor.util.executeThread
import java.util.*

class ParentRepository(
    private val parentDao: ParentDao
) {

    companion object{
        @Volatile
        private var instance: ParentRepository? = null

        fun getInstance(context: Context): ParentRepository? {
            return instance ?: synchronized(ParentRepository::class.java) {
                if(instance == null){
                    val database = GrowthMonitorDataBase.getInstance(context)
                    instance = ParentRepository(database.parentDao())
                }
                return instance
            }
        }
    }

    fun getParent(idParent: Long): Parent?{
        return parentDao.getParent(idParent)
    }

    fun insert(parent: Parent): Long{
        return parentDao.insert(parent)
    }

    fun insert(parentChild: ParentChildrenCrossRef): Long{
        return parentDao.insert(parentChild)
    }

    fun update(parent: Parent): Int {
        parent.updateAt = Date()
        return parentDao.update(parent)
    }

//    Return specific children for Parent
    fun getChildren(): List<ParentChildren>{
        return parentDao.getChildren()
    }

    fun getAllParentsFilterBy(filter: SearchFilter): LiveData<List<Parent>>{
        return parentDao.getAllParentsFilterBy(getFilteredQuery(filter))
    }

    private fun getFilteredQuery(filter: SearchFilter): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder()
            .append("SELECT * FROM parent ")

        simpleQuery.append(when(filter){
            SearchFilter.FATHER -> "WHERE gender = 1"
            SearchFilter.MOTHER -> "WHERE gender = 2"
        })

        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun updateParent(childParent: ParentChildrenCrossRef){
            parentDao.insert(childParent)
    }

    fun removeParent(idChild: Long, idParent: Long) {
        executeThread { parentDao.removeParent(idChild, idParent) }
    }
}