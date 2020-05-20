package com.dog.childgrowthmonitor.data.child

import android.content.Context
import androidx.lifecycle.LiveData
import com.dog.childgrowthmonitor.data.AnthroPlusDataBase
import java.util.*

class ChildRepository(
    private val childDao: ChildDao
) {

    companion object{
        @Volatile
        private var instance: ChildRepository? = null

        fun getInstance(context: Context): ChildRepository? {
            return instance ?: synchronized(ChildRepository::class.java) {
                if(instance == null){
                    val database = AnthroPlusDataBase.getInstance(context)
                    instance = ChildRepository(database.childDao())
                }
                return instance
            }
        }
    }

    fun getChild(idChild: Long): LiveData<Child>?{
        return childDao.getChild(idChild)
    }

//    Return all kids in DB
    fun getChildren(): LiveData<List<Child>>{
        return childDao.getChildren()
    }

    fun insert(child: Child): Long{
        return childDao.insert(child)
    }

    fun update(child: Child): Int {
        child.updateAt = Date()
        return childDao.update(child)
    }

    fun getParents(child: Long): LiveData<ChildParents>{
        return childDao.getParents(child)
    }
}