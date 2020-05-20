package com.dog.childgrowthmonitor.data.user

import android.content.Context
import androidx.lifecycle.LiveData
import com.dog.childgrowthmonitor.data.AnthroPlusDataBase

class UserRepository(
    private val userDao: UserDao
) {

    companion object{
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(context: Context): UserRepository? {
            return instance ?: synchronized(UserRepository::class.java) {
                if(instance == null){
                    val database = AnthroPlusDataBase.getInstance(context)
//                    instance = UserRepository(database.userDao())
                }
                return instance
            }
        }
    }

    fun getUser(idUser: Long): User?{
        return userDao.getUser(idUser)
    }

    fun getAllUser(): LiveData<List<User>>{
        return userDao.getAllUser()
    }

    fun insert(user: User): Long{
        return userDao.insert(user)
    }

    fun update(user: User): Int {
        return userDao.update(user)
    }

//    fun getAllParentByFilter(filter: SearchFilter): LiveData<List<User>>{
//        return userDao.getAllParentByFilter(getFilteredQuery(filter))
//    }

//    private fun getFilteredQuery(filter: SearchFilter): SimpleSQLiteQuery {
//        val simpleQuery = StringBuilder()
//            .append("SELECT * FROM user ")
//
//        simpleQuery.append(when(filter){
//            SearchFilter.FATHER -> "WHERE parent = 1 AND gender = 1"
//            SearchFilter.MOTHER -> "WHERE parent = 1 AND gender = 2"
//        })
//
//        return SimpleSQLiteQuery(simpleQuery.toString())
//    }
}