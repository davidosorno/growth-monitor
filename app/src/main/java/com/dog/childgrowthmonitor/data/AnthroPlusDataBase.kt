package com.dog.childgrowthmonitor.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dog.tutorelf.data.Converters
import com.dog.childgrowthmonitor.data.activity.Activity
import com.dog.childgrowthmonitor.data.address.Address
import com.dog.childgrowthmonitor.data.address.AddressDao
import com.dog.childgrowthmonitor.data.address.location.city.City
import com.dog.childgrowthmonitor.data.address.location.city.CityDao
import com.dog.childgrowthmonitor.data.address.location.country.Country
import com.dog.childgrowthmonitor.data.address.location.country.CountryDao
import com.dog.childgrowthmonitor.data.address.location.state.State
import com.dog.childgrowthmonitor.data.address.location.state.StateDao
import com.dog.childgrowthmonitor.data.visit.Visit
import com.dog.childgrowthmonitor.data.visit.VisitDao
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildDao
import com.dog.childgrowthmonitor.data.parents.Parent
import com.dog.childgrowthmonitor.data.parents.ParentChildrenCrossRef
import com.dog.childgrowthmonitor.data.parents.ParentDao
import com.dog.childgrowthmonitor.util.DATABASE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Database(entities = [
        Child::class,
        Activity::class,
        Visit::class,
        Address::class,
        Country::class,
        State::class,
        City::class,
        Parent::class,
        ParentChildrenCrossRef::class
    ],
    version = 1
)

@TypeConverters(Converters::class)
abstract class AnthroPlusDataBase : RoomDatabase() {

    abstract fun addressDao(): AddressDao
    abstract fun cityDao(): CityDao
    abstract fun stateDao(): StateDao
    abstract fun countryDao(): CountryDao
    abstract fun visitDao(): VisitDao
    abstract fun childDao(): ChildDao
    abstract fun parentDao(): ParentDao


    companion object{
        private var INSTANCE: AnthroPlusDataBase? = null

        fun getInstance(context: Context): AnthroPlusDataBase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        AnthroPlusDataBase::class.java,
                        DATABASE_NAME
                    )
                        .addCallback(DataBaseCallBack())
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }

    private class DataBaseCallBack(): RoomDatabase.Callback(){

        private val uiScope = CoroutineScope(Dispatchers.Main)

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {_ ->
//                uiScope.launch {
////                    prepopulateCountries()
//                }
            }
        }

        private fun prepopulateCountries() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}