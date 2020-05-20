package com.dog.childgrowthmonitor.data.address.location.state

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dog.childgrowthmonitor.data.BasicDao
import com.dog.childgrowthmonitor.data.address.location.country.StatesOfCountry

@Dao
interface StateDao: BasicDao<State> {

    @Transaction
    @Query("SELECT * FROM country")
    fun getStatesOfCountry(): List<StatesOfCountry>
}