package com.dog.childgrowthmonitor.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dog.childgrowthmonitor.data.activity.Activity
import com.dog.childgrowthmonitor.data.address.Address
import com.dog.childgrowthmonitor.data.address.AddressDao
import com.dog.childgrowthmonitor.data.address.location.city.City
import com.dog.childgrowthmonitor.data.address.location.city.CityDao
import com.dog.childgrowthmonitor.data.address.location.country.Country
import com.dog.childgrowthmonitor.data.address.location.country.CountryDao
import com.dog.childgrowthmonitor.data.address.location.state.State
import com.dog.childgrowthmonitor.data.address.location.state.StateDao
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildDao
import com.dog.childgrowthmonitor.data.parents.Parent
import com.dog.childgrowthmonitor.data.parents.ParentChildrenCrossRef
import com.dog.childgrowthmonitor.data.parents.ParentDao
import com.dog.childgrowthmonitor.data.visit.Visit
import com.dog.childgrowthmonitor.data.visit.VisitDao
import com.dog.childgrowthmonitor.util.DATABASE_NAME
import com.dog.tutorelf.data.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject


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
abstract class GrowthMonitorDataBase : RoomDatabase() {

    abstract fun addressDao(): AddressDao
    abstract fun cityDao(): CityDao
    abstract fun stateDao(): StateDao
    abstract fun countryDao(): CountryDao
    abstract fun visitDao(): VisitDao
    abstract fun childDao(): ChildDao
    abstract fun parentDao(): ParentDao


    companion object{
        private var INSTANCE: GrowthMonitorDataBase? = null

        fun getInstance(context: Context): GrowthMonitorDataBase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        GrowthMonitorDataBase::class.java,
                        DATABASE_NAME
                    )
                        .addCallback(DataBaseCallBack(context))
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }

    private class DataBaseCallBack(val context: Context): RoomDatabase.Callback(){

        private val uiScope = CoroutineScope(Dispatchers.Main)

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {_ ->
                uiScope.launch {
                    prepopulateDatabase()
                }
            }
        }

        private suspend fun prepopulateDatabase() {
            withContext(Dispatchers.IO) {
                var jsonString: String =
                    context.assets.open("countries.json").bufferedReader().use { it.readText() }
                var jsonObject: JSONObject = JSONObject(jsonString)
                storeCountries(jsonObject)

                jsonString = context.assets.open("states.json").bufferedReader().use { it.readText() }
                jsonObject = JSONObject(jsonString)
                storeStates(jsonObject)

                jsonString = context.assets.open("cities.json").bufferedReader().use { it.readText() }
                jsonObject = JSONObject(jsonString)
                storeCities(jsonObject)
            }
        }

        private fun storeCities(jsonObject: JSONObject) {
            val jsonArray: JSONArray = jsonObject.getJSONArray("cities")
            val listData = mutableListOf<City>()
            for(i in 0 until jsonArray.length()){
                val jo: JSONObject = jsonArray.getJSONObject(i)
                listData.add(
                    City(
                        jo.getLong("id"),
                        jo.getString("name"),
                        jo.getString("state_id").toLong()
                    )
                )
            }
            getInstance(context).cityDao().insertAll(*listData.toTypedArray())
        }

        private fun storeStates(jsonObject: JSONObject) {
            val jsonArray: JSONArray = jsonObject.getJSONArray("states")
            val listData = mutableListOf<State>()
            for(i in 0 until jsonArray.length()){
                val jo: JSONObject = jsonArray.getJSONObject(i)
                listData.add(
                    State(
                        jo.getLong("id"),
                        jo.getString("name"),
                        jo.getString("country_id").toLong()
                    )
                )
            }
            getInstance(context).stateDao().insertAll(*listData.toTypedArray())
        }

        private fun storeCountries(jsonObject: JSONObject) {
            val jsonArray: JSONArray = jsonObject.getJSONArray("countries")
            val listData = mutableListOf<Country>()
            for(i in 0 until jsonArray.length()){
                val jo: JSONObject = jsonArray.getJSONObject(i)
                listData.add(
                    Country(
                        jo.getLong("id"),
                        jo.getString("name"),
                        jo.getInt("phoneCode")
                    )
                )
            }
            getInstance(context).countryDao().insertAll(*listData.toTypedArray())
        }
    }
}